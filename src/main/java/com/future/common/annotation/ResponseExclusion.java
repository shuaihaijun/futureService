package com.future.common.annotation;

import org.springframework.core.MethodParameter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 放行使用此注解的controller方法，即不执行统一响应处理{@link }
 *
 * @author Admin
 * @version: 1.0
 * @see (MethodParameter, Class)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseExclusion {
}
