package com.heyya.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        checkContext();
        return context.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        checkContext();
        return context.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        checkContext();
        return context.getBean(clazz);
    }

    private static void checkContext() {
        if (Objects.isNull(context)) {
            throw new RuntimeException("The applicationContext is not initialized!");
        }
    }
}
