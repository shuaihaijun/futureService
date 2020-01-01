package com.future.common.enums;


/**
 * 用户模块统一返回状态码及描述信息
 * 用户模块错误码区间：200000-299999
 *
 * @author Admin
 * @version: 1.0
 */
public enum UserResultCode implements ResultCode {
    /*  */
    USER_MODULE_ERROR(200000, "用户模块异常"),
    USER_NOTEXIST_ERROR(200001, "用户不存在"),
    LOGINISEXIST(200002, "登录名已存在"),
    IDCARDISEXIST(200003, "身份证号码重复"),
    TELPHONEISEXIST(200004, "手机号码重复"),
    SEX_DATA_ERROR(200005, "性别信息错误"),
    NOWADDRESS(200006, "现住址信息不完整"),
    DATA_ERROR_BY_USERTEL(200007, "通过手机号码查询用户信息有误，目前存在多个该手机号码的用户！"),
    MEMBER_IMPROVING_MASTER_POST_INFO(200008, "请先完善人员信息！"),
    USER_PASSWORD_ERROR(200009, "用户名或密码错误！"),
    USER_STATE_EXCEPTION(200010, "用户状态异常！"),
    USER_INTRODUCE_NOTEXIST_ERROR(200010, "用户邀请码不存在！"),
    PASSWORD_ERROR(200110, "用户密码错误！");

    /**
     * 全局异常状态码
     * 状态码规则：状态码是6位长度的字符串。示例：1 01 100
     * 1：应用标记（例如组织机构应用或者人员管理应用）
     * 01：应用下的模块（例组织机构下的获取机构数据）
     * 100：定义的业务异常
     */


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 构造器
     */
    UserResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     */
    @Override
    public Integer code() {
        return this.code;
    }

    /**
     * 获取提示信息
     */
    @Override
    public String message() {
        return this.message;
    }

    /**
     * 通过枚举属性名称获取提示信息
     */
    public static String getMessage(String name) {
        for (UserResultCode item : UserResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    /**
     * 通过枚举名称获取状态码
     */
    public static Integer getCode(String name) {
        for (UserResultCode item : UserResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
