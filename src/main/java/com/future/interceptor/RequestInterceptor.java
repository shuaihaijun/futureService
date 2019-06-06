package com.future.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.future.common.CommonConstant;
import com.future.common.RequestKey;
import com.future.constant.StatusCode;
import com.future.util.CommonUtil;
import com.future.util.SystemContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 对外接口统一处理拦截器,校验传输的JSON格式信息是否合法
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

	/*@Value("${verify.key}")
	private String verifeKey;
	@Value("${verify.thirdKey}")
	private String verifeThirdKey;*/

	/** Logger日志实例 */
	private static final Logger logger = LoggerFactory
			.getLogger(RequestInterceptor.class);

	/**
	 * 方法调用后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object obj, Exception exception)
			throws Exception {
		SystemContext.removeRequestJSON();
	}

	/**
	 * 方法调用执行
	 */
	@Override
	public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 方法调用前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object obj) throws Exception {
		response.setCharacterEncoding(CommonConstant.UTF8_ENCODING);
		response.setContentType(CommonConstant.CONTENTTYPE_JSON);
		JSONObject requestJson = null;
		Map requestMap=new HashMap();
		if(request.getParameterMap()!=null&&request.getParameterMap().size()>0){
			requestMap=getParameterStringMap(request.getParameterMap());
			requestJson = JSONObject.parseObject(JSONObject.toJSONString(requestMap));
		}else {
			String requestJSONStr = getHttpBody(request);
			// 校验请求信息，是否为json格式
			if (!CommonUtil.isJSONValid(requestJSONStr)) {
				requestJson = new JSONObject();
				requestJson.put(RequestKey.code.name(), StatusCode.FORMAT.getCode());
				requestJson.put(RequestKey.msg.name(), StatusCode.FORMAT.getMsg());
				response.getWriter().write(requestJson.toJSONString());
				return false;
			}
			// 字符串格式化为JSONObject
			requestJson = JSONObject.parseObject(requestJSONStr);
			requestMap=(Map) JSONObject.toJavaObject(requestJson,Map.class);
		}

		/*需要加密 解密*/

		SystemContext.setRequestJSON(requestJson);
		return true;
	}

	/**
	 * 获取post请求流中内容
	 * 
	 * @return 请求流中内容
	 * 
	 * @throws Exception
	 */
	private String getHttpBody(HttpServletRequest request) throws Exception {
		StringBuffer strbuffer = null;
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,
				CommonConstant.UTF8_ENCODING);
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		strbuffer = new StringBuffer();
		while ((s = br.readLine()) != null) {
			strbuffer.append(s);
		}
		return strbuffer.toString();
	}

	/**
	 * Map<String,String[]> 转  Map<String, String>
	 * @param properties
	 * @return
	 */
	public Map<String, String> getParameterStringMap(Map<String,String[]> properties) {
		 Map<String, String> returnMap = new HashMap<String, String>();
		 String name = "";
		 String value = "";
		 for (Map.Entry<String, String[]> entry : properties.entrySet()) {
				 name = entry.getKey();
				 String[] values = entry.getValue();
				 if (null == values) {
						 value = "";
					 } else if (values.length>1) {
						 for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
							 value = values[i] + ",";
							 }
						 value = value.substring(0, value.length() - 1);
					 } else {
						 value = values[0];//用于请求参数中请求参数名唯一
					 }
				 returnMap.put(name, value);

			 }
		 return returnMap;
	 }
}
