package com.future.service.mt;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前线程缓存工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class ThreadCache {

    // 构建本地缓存对象
    private static ThreadLocal<Map> threadLocal = new ThreadLocal<>();


    /**
     * 获取缓存，如果缓存为空初始化缓存
     * @return Map缓存对象
     */
    public static Map getResult() {
        Map map = threadLocal.get();
        //判断Session是否为空，如果为空，将创建一个session，并设置到本地线程变量中
        if (map == null || map.isEmpty()) {
            map = new HashMap();
            threadLocal.set(map);
        }
        return map;
    }


    /**
     * 清楚缓存，建议显示调用，避免内存溢出
     */
    public static void removeResult() {
        threadLocal.remove();
    }
}