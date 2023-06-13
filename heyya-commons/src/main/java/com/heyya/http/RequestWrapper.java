package com.heyya.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] requestBody = null;

    private ConcurrentHashMap<String, String[]> requestParamemterMap = new ConcurrentHashMap<>(16);

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSON getRequestBody() throws UnsupportedEncodingException {
        boolean isMomentmpty = null != requestBody && requestBody.length > 0;
        if (isMomentmpty) {
            String requestBodyStr = new String(requestBody, StandardCharsets.UTF_8);
            boolean isArray = requestBodyStr.startsWith("[");
            try {
                if (isArray) {
                    return JSON.parseArray(requestBodyStr);
                } else {
                    return JSON.parseObject(requestBodyStr);
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public void setRequestBody(JSON json) throws UnsupportedEncodingException {
        this.requestBody = json.toJSONString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (requestBody == null) {
            requestBody = new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public int getContentLength() {
        return requestBody.length;
    }

    @Override
    public long getContentLengthLong() {
        return requestBody.length;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (null != requestParamemterMap) {
            return requestParamemterMap;
        }
        return super.getParameterMap();
    }

    @Override
    public String getParameter(String name) {
        if (null != requestParamemterMap) {
            String[] values = requestParamemterMap.get(name);
            if (values == null || values.length == 0) {
                return null;
            }
            return values[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if (null != requestParamemterMap) {
            return new Enumeration<String>() {
                private String[] arr = requestParamemterMap.keySet().toArray(new String[0]);
                private int idx = 0;

                @Override
                public boolean hasMoreElements() {
                    return idx < arr.length;
                }

                @Override
                public String nextElement() {
                    return arr[idx++];
                }

            };
        }
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        if (null != requestParamemterMap) {
            String[] arr = requestParamemterMap.get(name);
            if (arr == null) {
                return null;
            }
            return arr.clone();
        }
        return super.getParameterValues(name);
    }

    public void addParamemters(Map<String, String[]> parameters) {
        synchronized (requestParamemterMap) {
            if (requestParamemterMap.isEmpty()) {
                requestParamemterMap.putAll(super.getParameterMap());
            }
            requestParamemterMap.putAll(parameters);
        }
    }

}
