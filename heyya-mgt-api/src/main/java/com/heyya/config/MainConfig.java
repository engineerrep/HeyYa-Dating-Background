package com.heyya.config;

import com.heyya.interceptor.UserAuthConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserAuthConfig.class})
@MapperScan("com.heyya.mapper")
public class MainConfig {}
