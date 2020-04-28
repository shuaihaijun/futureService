package com.future.common.enums;


/**
 * 订单交易错误码(与trade项目同步)
 * @author Admin
 * @version: 1.0
 */
public enum TradeErrorEnum {

    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    ACC_DIS_CONNECT(11, "用户未连接"),
    ACC_QUOTE_GET_ERROR(12, "获取行情信息失败"),
    TRADE_TIMEOUT(21, "交易超时"),
    TRADE_NOT_ALLOWED(22, "账户不允许交易"),
    TRADE_PARAM_NULL_ERROR(23, "参数为空"),
    ORDER_OPEN_SYN_FAIL(31, "订单open异步提交失败"),
    ORDER_OPEN_FAIL(32, "订单open同步提交失败"),
    ORDER_CLOSE_SYN_FAIL(33, "订单close异步提交失败"),
    ORDER_CLOSE_FAIL(34, "订单close同步提交失败"),
    ORDER_VOLUME_ERROR(35, "订单手数计算错误"),
    ORDER_MAGIC_ERROR(36, "订单magic计算错误"),
    ORDER_CMD_ERROR(37, "订单类型错误"),
    FOLLOW_RULE_DATA_ERROR(41, "跟单规则获取异常"),
    FOLLOW_ORDER_TYPE_MATCH_ERROR(42, "跟单订单类型匹配异常"),
    FOLLOW_RULE_DEAL_ERROR(99, "跟单逻辑处理异常");


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
