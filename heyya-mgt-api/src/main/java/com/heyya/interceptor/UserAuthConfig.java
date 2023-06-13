package com.heyya.interceptor;

import com.heyya.config.auth.UserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(UserAuthProperties.class)
public class UserAuthConfig implements WebMvcConfigurer {
    @Autowired
    private UserAuthProperties userAuthProperties;

    @Bean
    public UserAuthContextInterceptor userActionContextInterceptor() {
        return new UserAuthContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userActionContextInterceptor())
                .addPathPatterns(userAuthProperties.getPathPatterns())
                .excludePathPatterns(userAuthProperties.getExcludePathPatterns());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui/index.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html/**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/v1/static/**").addResourceLocations("classpath:/static/");
    }
}
