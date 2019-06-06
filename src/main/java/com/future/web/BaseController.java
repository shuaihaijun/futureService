package com.future.web;


import com.future.common.Response;
import com.future.common.ResponseData;
import com.future.common.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller基类,提供了获取请求体内容等方法
 * 
 * @author haijun
 */
public class BaseController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected Response result(ResultMsg msg){
		return new Response(msg);
	}

	protected ResponseData responseData(ResultMsg msg, Object obj){
		return new ResponseData(msg, obj);
	}
	protected Response result(){
		return new Response();
	}

}