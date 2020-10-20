package com.wjf.config;

import com.wjf.jwt.JWTInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
        .excludePathPatterns("/login")//开放登录请求
                .addPathPatterns("/**"); //拦截请求
    }
}
