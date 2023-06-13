package com.heyya.http.pool.cmd.http.handler.impl;

import com.heyya.http.pool.consts.CharsetUtil;
import com.heyya.http.pool.consts.HttpMethod;
import com.heyya.http.pool.consts.HttpParamConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpPostHandler extends BaseHttpHandler<HttpPost> {

	public HttpPostHandler(CloseableHttpClient httpClient) {
		super(httpClient);
	}

	@Override
	protected void handleParams(HttpPost request, Map<String, String> params) {
		String format = params.remove(HttpParamConst.FORMAT_KEY);
		request.setEntity(handleParams(params, format));
	}

	@Override
	protected HttpPost createHttpRequest(String url, Map<String, String> params) {
		return new HttpPost(url);
	}

	private HttpEntity handleParams(Map<String, String> params, String format) {
		if(StringUtils.isNotBlank(format)) {
			String content = params.get(HttpParamConst.CONTENT_KEY);
			StringEntity requestEntity = new StringEntity(content, CharsetUtil.UTF_8);
			requestEntity.setContentEncoding(CharsetUtil.UTF_8.name());
			requestEntity.setContentType(format);
	        return requestEntity;
		} else {
            List<NameValuePair> list = new ArrayList<>();
            for(Map.Entry<String,String> entry: params.entrySet()){
                list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
			return new UrlEncodedFormEntity(list,CharsetUtil.UTF_8);
		}
	}

	@Override
	protected HttpMethod getMethod() {
		return HttpMethod.POST;
	}

}
