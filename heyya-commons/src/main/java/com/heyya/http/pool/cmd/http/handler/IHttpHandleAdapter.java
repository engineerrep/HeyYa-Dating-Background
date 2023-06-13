package com.heyya.http.pool.cmd.http.handler;

import com.heyya.http.pool.consts.HttpMethod;

import java.util.Map;

public interface IHttpHandleAdapter {

    boolean isMatcher(HttpMethod method);

    String execute(String url, Map<String, String> params);

    String execute(String url, Map<String, String> headers, Map<String, String> params);

}
