package com.future.config;

import com.future.common.filter.RequestParamsFilter;
import com.future.common.interceptor.CORSInterceptor;
import com.future.common.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * 注册拦截器 
 * Created by SYSTEM on 2017/8/16. 
 */
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;
    @Autowired
    CORSInterceptor corsInterceptor;

    /**
     * addPathPatterns 添加拦截规则
     * excludePathPatterns 排除拦截规则
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**/*");
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**/*");
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RequestParamsFilter());
        registration.addUrlPatterns("/*"); //
        registration.setName("RequestParamsFilter");
        return registration;
    }
}