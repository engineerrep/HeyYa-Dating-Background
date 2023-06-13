package com.heyya.http.pool.cmd.http;

import com.heyya.http.pool.cmd.IHttpCmd;
import com.heyya.http.pool.cmd.http.handler.IHttpHandleAdapter;
import com.heyya.http.pool.consts.HttpMethod;

import java.util.Map;

public class DefaultHttpCmdImpl implements IHttpCmd {

    private final IHttpHandleAdapter[] httpHandlers;

    public DefaultHttpCmdImpl(IHttpHandleAdapter[] httpHandlers) {
        boolean isEmpty = null == httpHandlers || httpHandlers.length <= 0;
        if (isEmpty) {
            throw new RuntimeException("httpHandlers cannot be empty");
        }
        this.httpHandlers = httpHandlers;
    }

    @Override
    public String execute(String url, HttpMethod httpMethod, Map<String, String> params) {
        for (IHttpHandleAdapter adapter : httpHandlers) {
            if (adapter.isMatcher(httpMethod)) {
                return adapter.execute(url, params);
            }
        }
        throw new RuntimeException(String.format("the method %s have not handler. request failed.", httpMethod));
    }

    @Override
    public String execute(String url, HttpMethod httpMethod, Map<String, String> headers, Map<String, String> params) {
        for (IHttpHandleAdapter adapter : httpHandlers) {
            if (adapter.isMatcher(httpMethod)) {
                return adapter.execute(url, headers, params);
            }
        }
        throw new RuntimeException(String.format("the method %s have not handler. request failed.", httpMethod));
    }

}
