package com.future.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统上下文工具类,用于存放当前请求,线程中属性值
 *
 *@author vinceyu
 */
public class SystemContext {

	/** 存放POST请求体内容 */
	private static ThreadLocal<JSONObject> requestJSON = new ThreadLocal<JSONObject>();

	public static JSONObject getRequestJSON() {
		JSONObject jobj = requestJSON.get();
		if (jobj == null) {
			return null;
		}
		return jobj;
	}

	public static void setRequestJSON(JSONObject _requestJSON) {
		requestJSON.set(_requestJSON);
	}

	public static void removeRequestJSON() {
		requestJSON.remove();
	}
}