package com.future.common.enums;


/**
 * 用户模块统一返回状态码及描述信息
 * 用户模块错误码区间：200000-299999
 *
 * @author Admin
 * @version: 1.0
 */
public enum OrderTypeCode implements ResultCode {
    /*  */
    ORDER_TYPE_BUY(0, "OP_BUY"),
    ORDER_TYPE_SELL(1, "OP_SELL"),
    ORDER_TYPE_BUYLIMIT(2, "OP_BUY_LIMIT"),
    ORDER_TYPE_SELLLIMIT(3, "OP_SELL_LIMIT"),
    ORDER_TYPE_BUYSTOP(4, "OP_BUY_STOP"),
    ORDER_TYPE_SELLSTOP(5, "OP_SELL_STOP"),
    ORDER_TYPE_BALANCE(6, "OP_BALANCE"),
    ORDER_TYPE_CREDIT(7, "OP_CREDIT"),
    ORDER_TYPE_OP_BUY_STOP_LIMIT(8, "OP_BUY_STOP_LIMIT"),
    ORDER_TYPE_OP_SELL_STOP_LIMIT(9, "OP_SELL_STOP_LIMIT");

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
    OrderTypeCode(Integer code, String message) {
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
        for (OrderTypeCode item : OrderTypeCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    /**
     * 通过枚举值获取状态码
     */
    public static Integer getCode(String message) {
        for (OrderTypeCode item : OrderTypeCode.values()) {
            if (item.message.equals(message)) {
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
