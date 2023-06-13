package com.heyya.tencent.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.heyya.http.pool.IHttpClient;
import com.heyya.http.pool.consts.HttpMethod;
import com.heyya.tencent.anatations.ReqMethod;
import com.heyya.tencent.consts.TencentImConsts;
import com.heyya.tencent.req.BaseReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Validated
@Service
public class TencentClient {
    @Autowired
    private IHttpClient httpClient;

    private final static String OK = "OK";

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(100000000);
    }

    private static String imRequestUrl(String url, String userSig, String identifier, String sdkappid) {
        StringBuilder imUrl = new StringBuilder();
        String nUrl = url.startsWith("/") ? url.substring(1) : url;
        imUrl.append(TencentImConsts.IM_URL).append(nUrl)
                .append("?usersig=").append(userSig)
                .append("&identifier=").append(identifier)
                .append("&sdkappid=").append(sdkappid)
                .append("&random=").append(randomInt())
                .append("&contenttype=").append("json");
        return imUrl.toString();
    }

    public <T extends BaseReq> Boolean sendMsg(@Valid T req) {
        if (!req.getClass().isAnnotationPresent(ReqMethod.class)) {
            throw new RuntimeException(String.format("%s defined error. please add the @ReqMethod ", req.getClass().getName()));
        }
        ReqMethod method = req.getClass().getAnnotation(ReqMethod.class);
        String reqUrl = imRequestUrl(method.url(), req.getUserSig(), req.getIdentifier(), req.getSdkappid());
        String response = null;
        if (HttpMethod.POST == method.method()) {
            response = httpClient.postForJson(reqUrl,
                    JSON.toJSONString(req));
        } else if (HttpMethod.GET == method.method()) {
            Map<String, String> params = JSON.parseObject(JSON.toJSONString(req), new TypeReference<Map<String, String>>() {
            });
            response = httpClient.get(reqUrl, params);
        }
        log.info("Request parameters{}:", JSON.toJSONString(req));
        log.info("the result is {}", response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        return OK.equals(jsonObject.getString("ActionStatus"));
    }

}