package com.future.common.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * JSON工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class JsonUtils {

    /**
     * 判断json字符串是否是json格式
     *
     * @param test
     * @return
     */
    public final static boolean isJson(String test) {
        try {
            JSONObject.parseObject(test);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断json对象中保存的ke 是 JSONArray 还是 JSONObject
     * @param jsonObject
     * @param key
     * @return
     */
    public final static boolean isThisJSONArray(JSONObject jsonObject,String key) {
        try {
            Object object=jsonObject.get(key);
            if(object instanceof JSONArray){
                return true;
            }else {
                return false;
            }
        } catch (JSONException ex) {
            return false;
        }
    }

    /**
     * 将json字符串转化为对象
     *
     * @param text
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 将json字符串转化为数组
     *
     * @param text
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将json字符串转化为map
     *
     * @param text
     * @return
     */
    public static Map<String, Object> parseMap(String text) {
        return JSON.parseObject(text);
    }

    /**
     * 将对象转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }
}