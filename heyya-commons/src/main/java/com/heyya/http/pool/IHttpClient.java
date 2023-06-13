package com.heyya.http.pool;

import java.util.Map;

public interface IHttpClient {

	String post(String url, Map<String, String> params);

	String post(String url, Map<String, String> headers, Map<String, String> params);

	String postForJson(String url, String jsonStr);

	String postForJson(String url, Map<String, String> headers, String jsonStr);

	String postForXml(String url, String xml);

	String postForXml(String url, Map<String, String> headers, String xml);

	String get(String url, Map<String, String> params);

	String get(String url, Map<String, String> headers, Map<String, String> params);
}
