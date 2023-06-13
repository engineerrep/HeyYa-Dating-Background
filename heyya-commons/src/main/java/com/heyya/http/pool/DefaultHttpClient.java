package com.heyya.http.pool;

import com.heyya.http.pool.cmd.IHttpCmd;
import com.heyya.http.pool.consts.HttpMethod;
import com.heyya.http.pool.consts.HttpParamConst;
import com.heyya.http.pool.consts.MediaType;

import java.util.HashMap;
import java.util.Map;

public class DefaultHttpClient implements IHttpClient{

	private final IHttpCmd httpCmd;
	public DefaultHttpClient(IHttpCmd httpCmd) {
		this.httpCmd = httpCmd;
	}
	
	@Override
	public String post(String url, Map<String, String> params) {
		return post(url, null, params);
	}

	@Override
	public String post(String url, Map<String, String> headers, Map<String, String> params) {
		return httpCmd.execute(url, HttpMethod.POST, headers, params);
	}

	@Override
	public String get(String url, Map<String, String> params) {
		return get(url, null, params);
	}

	@Override
	public String get(String url, Map<String, String> headers, Map<String, String> params) {
		return httpCmd.execute(url, HttpMethod.GET, headers, params);
	}

	@Override
	public String postForJson(String url,  String jsonStr) {
		return postForJson(url, null, jsonStr);
	}

	@Override
	public String postForJson(String url, Map<String, String> headers, String jsonStr) {
		Map<String, String> params = new HashMap<>(4);
		params.put(HttpParamConst.FORMAT_KEY, MediaType.APPLICATION_JSON);
		params.put(HttpParamConst.CONTENT_KEY, jsonStr);
		return httpCmd.execute(url, HttpMethod.POST, headers, params);
	}

	@Override
	public String postForXml(String url, String xml) {
		return postForXml(url, null, xml);
	}

	@Override
	public String postForXml(String url, Map<String, String> headers, String xml) {
		Map<String, String> params = new HashMap<>(4);
		params.put(HttpParamConst.FORMAT_KEY, MediaType.TEXT_XML);
		params.put(HttpParamConst.CONTENT_KEY, xml);
		return httpCmd.execute(url, HttpMethod.POST, params);
	}


}
