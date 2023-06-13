package com.heyya.http.pool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Map;

@ConfigurationProperties(prefix = "heyya.http.request")
@Data
public class RequestProperties {

    private Integer connectionRequestTimeout = -1;

    private Integer socketTimeout = 5000;

    private Integer connectTimeout = 5000;

    private Integer maxTotal = 1000;

    private Integer maxPerRoute = 200;

    private Integer maxIdleTime = 5000;

    private Integer maxLiveTime = 60;

    private Integer maxEvictTime = 30;

    private Integer validateTime = 2;

    private Map<String, Integer> hostsPerRoute = Collections.emptyMap();

    private Integer maxRetryTimes = 0;

}
