package com.future.common.enums;


/**
 * 订单交易错误码(与trade项目同步)
 * @author Admin
 * @version: 1.0
 */
public enum TradeErrorEnum {

    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    TRADE_TIMEOUT(2, "交易超时"),
    ACC_DIS_CONNECT(2, "用户未连接"),
    TRADE_NOT_ALLOWED(3, "账户不允许交易"),
    QUOTE_GET_ERROR(4, "获取行情信息"),
    ORDER_OPEN_SYN_FAIL(5, "订单open异步提交失败"),
    ORDER_OPEN_FAIL(6, "订单open同步提交失败"),
    ORDER_CLOSE_SYN_FAIL(7, "订单close异步提交失败"),
    ORDER_CLOSE_FAIL(8, "订单close同步提交失败"),
    PARAM_NULL_ERROR(9, "参数为空"),
    FOLLOW_RULE_DATA_ERROR(9, "跟单规则获取异常"),
    FOLLOW_ORDER_TYPE_MATCH_ERROR(9, "跟单订单类型匹配异常"),
    FOLLOW_RULE_DEAL_ERROR(9, "跟单逻辑处理异常");


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
    TradeErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     */
    public Integer code() {
        return this.code;
    }

    /**
     * 获取提示信息
     */
    public String message() {
        return this.message;
    }

    /**
     * 通过枚举属性名称获取提示信息
     */
    public static String getMessage(String name) {
        for (TradeErrorEnum item : TradeErrorEnum.values()) {
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
        for (TradeErrorEnum item : TradeErrorEnum.values()) {
            if (item.message.equals(message)) {
                return item.code;
            }
        }
        return null;
    }

    /**
     * 通过枚举值获取状态码
     */
    public static String getMessage(Integer code) {
        for (TradeErrorEnum item : TradeErrorEnum.values()) {
            if (item.code.equals(code)) {
                return item.message;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
