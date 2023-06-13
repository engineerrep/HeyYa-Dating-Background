package com.heyya.http.pool.cmd.http.handler.impl;

import com.heyya.http.pool.consts.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;

@Slf4j
public class HttpGetHandler extends BaseHttpHandler<HttpGet> {

    public HttpGetHandler(CloseableHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    protected HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected void handleParams(HttpGet request, Map<String, String> params) {
    }

    @Override
    protected HttpGet createHttpRequest(String url, Map<String, String> params) {
        return new HttpGet(explainUrl(url, params));
    }

    private String explainUrl(String url, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder urlSubfix = new StringBuilder();
        boolean isHasParam = url.contains("?");
        if (!isHasParam) {
            urlSubfix.append("?a=").append(Math.random());
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            urlSubfix.append("&").append(key).append("=").append(value);
        }
        url += urlSubfix.toString();
        return url;
    }

}
