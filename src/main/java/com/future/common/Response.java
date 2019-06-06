package com.future.common;

public class Response {


	/** 返回信息码*/
	private String code="0";
	/** 返回信息内容*/
	private String msg="操作成功";

	public Response() {
	}
	
	public Response(ResultMsg msg){
		this.code=msg.getCode();
		this.msg=msg.getMsg();
	}
	
	public Response(String code) {
		this.code = code;
		this.msg = "";
	}

	public Response(String code, String Msg) {
		this.code = code;
		this.msg = Msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String Msg) {
		this.msg = Msg;
	}

	@Override
	public String toString() {
		return "Response{" +
				"code='" + code + '\'' +
				", msg='" + msg + '\'' +
				'}';
	}
}
