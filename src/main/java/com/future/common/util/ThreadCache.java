package com.future.common.util;


/**
 * 当前线程缓存工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class ThreadCache {

    // ThreadLocal里只存储了简单的String对象，也可以自己定义对象，存储更加复杂的参数

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static String getPostRequestParams() {
        return threadLocal.get();
    }

    public static void setPostRequestParams(String postRequestParams) {
        threadLocal.set(postRequestParams);
    }

    public static void removePostRequestParams() {
        threadLocal.remove();
    }
}