package com.heyya.http.pool.cmd.http.handler.impl;

import com.heyya.http.pool.cmd.http.handler.IHttpHandleAdapter;
import com.heyya.http.pool.consts.CharsetUtil;
import com.heyya.http.pool.consts.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Map;

@Slf4j
public abstract class BaseHttpHandler<R extends HttpRequestBase> implements IHttpHandleAdapter {

    protected final CloseableHttpClient httpClient;

    public BaseHttpHandler(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public boolean isMatcher(HttpMethod method) {
        return getMethod() == method;
    }

    protected abstract HttpMethod getMethod();

    @Override
    public String execute(String url, Map<String, String> headers, Map<String, String> params) {
        R request = createHttpRequest(url, params);
        if (null != headers && !headers.isEmpty()) {
            headers.forEach((k, v) -> {
                request.addHeader(k, v);
            });
        }
        handleParams(request, params);
        try (CloseableHttpResponse response = httpClient.execute(request, HttpClientContext.create())) {
            HttpEntity resEntity = response.getEntity();
            String result = EntityUtils.toString(resEntity, CharsetUtil.UTF_8);
            EntityUtils.consume(resEntity);
            return result;
        } catch (Exception e) {
            log.error("http request failed! the error={}", e);
        }
        return null;
    }

    @Override
    public String execute(String url, Map<String, String> params) {
        return execute(url, null, params);
    }

    protected abstract void handleParams(R request, Map<String, String> params);

    protected abstract R createHttpRequest(String url, Map<String, String> params);

}
