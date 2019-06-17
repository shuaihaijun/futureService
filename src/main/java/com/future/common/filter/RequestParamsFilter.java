package com.future.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.future.common.constants.GlobalConstant;
import com.future.common.util.LogUtil;
import com.future.common.util.ThreadCache;
import com.future.common.util.WrappedHttpServletRequest;

/**
 * 拦截请求，将请求参数存放到本地线程中
 * 解决请求输入流仅读一次后再次获取失效问题（基于{@link ThreadLocal}实现）
 *
 * @author Admin
 * @version: 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "InterfaceFilter")
public class RequestParamsFilter implements Filter {

    private static LogUtil logger = LogUtil.logger(RequestParamsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            WrappedHttpServletRequest wrappedHttpServletRequest = new WrappedHttpServletRequest(req);
            String contentType = req.getContentType();
            if (contentType != null) {
                contentType = contentType.toLowerCase();
            }

            if ("POST".equals(req.getMethod().toUpperCase()) && (GlobalConstant.PLATFORM_RESPONSE_CONTENTTYPE_JSON.equals(contentType) || GlobalConstant.CONTENTTYPE_JSON_UTF_8.equals(contentType))) {
                // 获取请求参数，保存到本地线程缓存中
                String params = wrappedHttpServletRequest.getRequestParams();
                ThreadCache.setPostRequestParams(params);
                logger.info("set request to ThreadCache : {}", params);
            } else {
                //logger.info("非post请求");
            }
            System.out.println("========" + 1);
            chain.doFilter(wrappedHttpServletRequest, response);
            System.out.println("========" + 2);
        } catch (Exception e) {
            System.out.println("========" + e.getMessage());
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {

    }
}