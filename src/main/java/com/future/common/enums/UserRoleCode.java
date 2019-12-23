package com.future.common.enums;


/**
 * 用户模块统一返回状态码及描述信息
 * 用户模块错误码区间：200000-299999
 *
 * @author Admin
 * @version: 1.0
 */
public enum UserRoleCode implements ResultCode {
    /*  */
    USER_ROLE_NORMAL(6, "普通用户"),
    USER_ROLE_IB(6, "初级代理"),
    USER_ROLE_MIB(6, "中级代理"),
    USER_ROLE_PIB(6, "高级代理"),
    USER_ROLE_SIGNAL(6, "信号源");

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
    UserRoleCode(Integer code, String message) {
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
        for (UserRoleCode item : UserRoleCode.values()) {
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
        for (UserRoleCode item : UserRoleCode.values()) {
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
