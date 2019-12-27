package com.future.config;

import com.future.common.filter.RequestParamsFilter;
import com.future.common.interceptor.CORSInterceptor;
import com.future.common.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * 注册拦截器 
 * Created by SYSTEM on 2017/8/16. 
 */
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {
        /*implements WebMvcConfigurer {*/
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的OTA目录下，访问路径如：http://localhost:8081/OTA/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中OTA表示访问的前缀。"file:D:/image/"是文件真实的存储路径
        registry.addResourceHandler("/image/**").addResourceLocations("file:D://image/");
    }
}