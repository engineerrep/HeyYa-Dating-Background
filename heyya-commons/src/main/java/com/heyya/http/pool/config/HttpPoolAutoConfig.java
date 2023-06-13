
package com.heyya.http.pool.config;

import com.heyya.http.pool.DefaultHttpClient;
import com.heyya.http.pool.cmd.IHttpCmd;
import com.heyya.http.pool.cmd.http.DefaultHttpCmdImpl;
import com.heyya.http.pool.cmd.http.handler.IHttpHandleAdapter;
import com.heyya.http.pool.cmd.http.handler.impl.HttpGetHandler;
import com.heyya.http.pool.cmd.http.handler.impl.HttpPostHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.rmi.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@ConditionalOnClass({ConnectionSocketFactory.class, HttpConnectionFactory.class, PoolingHttpClientConnectionManager.class})
@EnableConfigurationProperties(RequestProperties.class)
public class HttpPoolAutoConfig {

    @Autowired
    private RequestProperties requestProperties;

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(requestProperties.getConnectionRequestTimeout())
                .setConnectTimeout(requestProperties.getConnectTimeout())
                .setSocketTimeout(requestProperties.getSocketTimeout())
                .build();
    }

    @Bean
    public PoolingHttpClientConnectionManager poolManager() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
                        .build();
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory =
                new ManagedHttpClientConnectionFactory(
                        DefaultHttpRequestWriterFactory.INSTANCE,
                        DefaultHttpResponseParserFactory.INSTANCE);
        DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
        final PoolingHttpClientConnectionManager poolManager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);
        SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        poolManager.setDefaultSocketConfig(defaultSocketConfig);
        poolManager.setDefaultMaxPerRoute(requestProperties.getMaxPerRoute());
        if (null != requestProperties.getHostsPerRoute() && !requestProperties.getHostsPerRoute().isEmpty()) {
            requestProperties.getHostsPerRoute().forEach((host, maxPerRoute) -> {
                HttpHost httpHost = explainHost(host);
                boolean isValidRouteNum = null != maxPerRoute && maxPerRoute.intValue() > 0;
                if (null != httpHost && isValidRouteNum) {
                    poolManager.setMaxPerRoute(new HttpRoute(httpHost), maxPerRoute);
                }
            });
        }

        poolManager.setMaxTotal(requestProperties.getMaxTotal());
        poolManager.setValidateAfterInactivity(requestProperties.getValidateTime());
        return poolManager;
    }


    private HttpHost explainHost(String host) {
        if (StringUtils.isBlank(host)) {
            return null;
        }
        boolean isHttpProtocalUrl = host.startsWith("https://") || host.startsWith("http://");
        if (!isHttpProtocalUrl) {
            return null;
        }
        String hostname = host.split("/")[2];
        int port = 80;
        boolean isHasPort = hostname.contains(":");
        if (isHasPort) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        return new HttpHost(hostname, port);
    }

    @Bean
    public CloseableHttpClient httpClient(RequestConfig requestConfig, PoolingHttpClientConnectionManager poolManager) {
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(poolManager)
                .setConnectionManagerShared(Boolean.TRUE)
                .evictIdleConnections(requestProperties.getMaxEvictTime(), TimeUnit.SECONDS)
                .evictExpiredConnections()
                .setConnectionTimeToLive(requestProperties.getMaxLiveTime(), TimeUnit.SECONDS)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                .setRetryHandler(((ex, retryTimes, context) -> {
                    if (retryTimes >= requestProperties.getMaxRetryTimes()) {
                        return false;
                    }
                    if (ex instanceof NoHttpResponseException) {
                        return true;
                    }
                    if (ex instanceof SSLHandshakeException) {
                        return false;
                    }
                    if (ex instanceof InterruptedIOException) {
                        return false;
                    }
                    if (ex instanceof UnknownHostException) {
                        return false;
                    }
                    if (ex instanceof ConnectTimeoutException) {
                        return false;
                    }
                    if (ex instanceof SSLException) {
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                })).build();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                client.close();
            } catch (IOException e) {
                log.error("connection close failed! the error={}", e);
            }
        }));
        return client;
    }

    @Bean
    public IHttpCmd defaultHttpCmd(CloseableHttpClient httpClient) {
        IHttpHandleAdapter[] httpHandlers = new IHttpHandleAdapter[]{
                new HttpPostHandler(httpClient),
                new HttpGetHandler(httpClient)
        };
        return new DefaultHttpCmdImpl(httpHandlers);
    }

    @Bean
    public DefaultHttpClient defaultHttpClient(IHttpCmd httpCmd) {
        DefaultHttpClient client = new DefaultHttpClient(httpCmd);
        return client;
    }

}
