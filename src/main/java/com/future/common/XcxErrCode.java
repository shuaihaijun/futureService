package com.future.common;

/**
 * 小程序请求返回code
 */
public enum XcxErrCode {
			SUCCESS("0", "请求成功"),BUSY("-1", "系统繁忙，此时请开发者稍候再试"),
			URL_ERROR("40066", "url参数错误！"),CODE_USED("40163", "code已被使用！"),
			INVALID("40029","code 无效"),TOO_OFTEN("45011","频率限制，每个用户每分钟100次");

	private final String code;
	private final String desc;

	XcxErrCode(String code, String msg) {
		this.desc = msg;
		this.code = code;
	}

	public static String getDesc(String code) {
		XcxErrCode[] businessModeEnums = values();
		for (XcxErrCode modeEnum : businessModeEnums) {
			if (modeEnum.code.equals(code)) {
				return modeEnum.desc;
			}
		}
		return null;
	}

	public String getMsg() {
		return desc;
	}

	public String getCode() {
		return code;
	}
}