package com.heyya.config.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("user-auth")
public class UserAuthProperties {
    private String[] pathPatterns = {"/**"};
    private String[] excludePathPatterns = {"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**"};
    private Long tokenExpire = 7 * 24 * 60 * 60L;
}
