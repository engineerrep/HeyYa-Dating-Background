package com.heyya.http.pool.cmd;

import com.heyya.http.pool.consts.HttpMethod;

import java.util.Map;

public interface IHttpCmd {

    String execute(String url, HttpMethod httpMethod, Map<String, String> params);

    String execute(String url, HttpMethod httpMethod, Map<String, String> headers, Map<String, String> params);

}
