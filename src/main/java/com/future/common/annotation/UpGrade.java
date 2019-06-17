package com.future.common.annotation;

import org.springframework.core.MethodParameter;

import java.lang.annotation.*;

/**
 * 通过此注解判断是否放行、拦截外部请求
 *
 * @author Admin
 * @version: 1.0
 * @see (MethodParameter, Class)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpGrade {
    boolean value() default false;
}
