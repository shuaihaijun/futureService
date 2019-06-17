package com.future.common.handler;

import com.future.common.annotation.ResponseExclusion;
import com.future.common.result.DefaultErrorResult;
import com.future.common.util.AnnotationUtil;
import com.future.common.util.ResultUtil;
import com.future.common.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 拦截Controller实现统一响应{@link ServerHttpResponse}处理
 *
 * @author Admin
 * @version: 1.0
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object>, InitializingBean {

    @Value("${responseResultHandler.basePackages:null}")
    private String basePackages;

    /**
     * 是否支持响应类型，当返回true时执行beforeBodyWrite方法
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 放行ResultHandle注解的controller方法
        ResponseExclusion resultHandle = returnType.getMethodAnnotation(ResponseExclusion.class);
        if (resultHandle == null) {
            resultHandle = returnType.getMethod().getDeclaringClass().getAnnotation(ResponseExclusion.class);
        }
        return resultHandle == null;
    }

    /**
     * 处理响应内容
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof DefaultErrorResult) {
            return body;
        } else if (body instanceof PageInfo) {
            PageInfo<?> page = (PageInfo<?>) body;
            return ResultUtil.success(page);
        }
        return ResultUtil.success(body);
    }

    /**
     * 系统初始化时执行,仅拦截配置在responseResultHandler.basePackages属性中的Controlle
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isNotEmpty(basePackages) && !"null".equals(basePackages)) {
            AnnotationUtil.modifyFiled(this, ControllerAdvice.class, "basePackages", basePackages.split(","));
        }
    }

}