package com.future.common.util;


import com.future.common.enums.RequestKey;
import com.future.pojo.bo.AdminInfo;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 请求对象获取工具类
 * <P>登录信息需要登录功能配合实现，即登录时需要设置用户信息以及token信息</P>
 *
 * @author Admin
 * @since
 */
public class RequestContextHolderUtil {

    /**
     * 获取应用属性集合
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取请求体request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取返回体response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取会话session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取应用上下文
     */
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    /**
     * 获取登录人信息
     *
     * @return
     */
    public static AdminInfo getAdminInfo() {
        return (AdminInfo) getSession().getAttribute(RequestKey.loginAdminInfo.name());
    }

    /**
     * 获取登录token
     *
     * @return
     */
    public static String getAdmintoken() {
        return (String) getSession().getAttribute(RequestKey.admintoken.name());
    }


    /**
     * 设置登录人信息
     *
     * @return
     */
    public static void setAdminInfo(AdminInfo adminInfo) {
        getSession().setAttribute(RequestKey.loginAdminInfo.name(),adminInfo);
    }

    /**
     * 设置登录token
     *
     * @return
     */
    public static void setAdmintoken(String token) {
        getSession().setAttribute(RequestKey.admintoken.name(),token);
    }

    /**
     * 移除登录人信息
     *
     * @return
     */
    public static void removeAdminInfo() {
        getSession().removeAttribute(RequestKey.loginAdminInfo.name());
    }

    /**
     * 移除登录token
     *
     * @return
     */
    public static void removeAdmintoken() {
        getSession().removeAttribute(RequestKey.admintoken.name());
    }
}