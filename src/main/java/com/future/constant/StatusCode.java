package com.future.constant;

/**
 * 服务接口响应状态码枚举类
 * 
 */
public enum StatusCode {
			DEAL_SUCCESS("0", "业务处理成功"),DEAL_FAIL("1", "业务处理错误"),
			PARAMETER("2", "请求参数错误"), AUTHENTICATION("3", "认证失败"),
			NULL("4", "数据集为空"), TYPENONEXIST("5", "service不存在"),
			OPERNONEXIST("6", "方法不存在"), EXCEPTION("7", "执行异常"),
			REQUEST("8", "请求失效"), FORMAT("9", "请求格式错误"),
			DATA_MISS("11","请求数据丢失"),FAIL("12","请求处理失败"),
			SUCCESS("200", "成功");

	private final String code;
	private final String msg;

	StatusCode(String code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}
}