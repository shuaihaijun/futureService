package com.future.common.interceptor;

import com.future.common.util.LogUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域拦截器类，允许跨域访问
 *
 * @author Admin
 * @version: 1.0
 */
@Component
public class CORSInterceptor implements HandlerInterceptor {

    private static LogUtil logger = LogUtil.logger(HandlerInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("cors intercepter!");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //这里“Access-Token”是我要传到后台的内容key
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,token");
        logger.debug("跨域设置已开启");

        return true;
    }
}