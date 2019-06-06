
package com.future.common;
/**
 * @description 返回消息码
 * @author haijun
 * @date 2019/4/13
 * @param
 */
public enum ResultMsg {
	SUCCESS("0", "操作成功"),
	FAILED("1","操作失败"),
	LOGININVALID("2", "用户未登录！"),
    ParamError("3", "参数错误！"),
    ParamValidError("4", "参数格式错误！")
    ;

	private String code;
	private String msg;


	ResultMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    
}

