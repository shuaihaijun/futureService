package com.future.common.constants;


/**
 * 全局常量定义
 *
 * @author Admin
 * @version: 1.0
 */
public class GlobalConstant {

    /**
     * 请求数据字符编码
     */
    public static final String PLATFORM_CHARACTER_ENCODING = "UTF-8";
    /**
     * 请求数据格式类型
     */
    public static final String PLATFORM_RESPONSE_CONTENTTYPE_JSON = "application/json";
    /**
     * 请求数据格式类型
     */
    public static final String CONTENTTYPE_JSON_UTF_8 = "application/json;charset=utf-8";


    // mt账户连接
    public static final String TRADE_ACC_SET_CONNECT = "/trader/account/setAccountConnect";
    // mt账户连接断开
    public static final String TRADE_ACC_DIS_CONNECT = "/trader/account/setAccountDisConnect";
    // mt账户连接
    public static final String TRADE_ACC_SIGNAL_MONITOR = "/trader/account/setSignalMonitor";
    // 获取用户close订单
    public static final String TRADE_ORDER_CLOSE_ORDERS = "/trader/order/getUserCloseOrders";
    // 获取用户close订单
    public static final String TRADE_ORDER_CLOSE_ORDER = "/trader/order/getUserCloseOrderById";
    // 获取用户open订单
    public static final String TRADE_ORDER_OPEN_ORDERS = "/trader/order/getUserOpenOrders";
    // 获取用户open订单
    public static final String TRADE_ORDER_OPEN_ORDER = "/trader/order/getUserOpenOrderById";
}
