package com.future.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class FuOrderCustomer {
    /**
     * 
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源））
     */
    private int userType;

    /**
     * 交易账号ID
     */
    private String mtId;

    /**
     * 外汇产品
     */
    private String orderSymbol;

    /**
     * 交易手数
     */
    private BigDecimal orderLots;

    /**
     * 订单类型（ 0 普通订单，1 拆分订单）
     */
    private int orderType;

    /**
     * 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 已平仓）
     */
    private int orderState;

    /**
     * 止损
     */
    private BigDecimal orderStoploss;

    /**
     * 获利
     */
    private BigDecimal orderTakeprofit;

    /**
     * 原订单号
     */
    private String orderSuperior;

    /**
     * 挂单时间
     */
    private Date orderExpiration;

    /**
     * 交易价格
     */
    private BigDecimal orderTradePrice;

    /**
     * 交易时间
     */
    private Date orderTradeDate;

    /**
     * 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     */
    private int orderTradeOperation;

    /**
     * 交易类型（0 建仓，1 平仓）
     */
    private int orderTradeType;

    /**
     * 平仓价格
     */
    private BigDecimal orderClosePrice;

    /**
     * 平仓时间
     */
    private Date orderCloseDate;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 修改时间
     * @return modify_date 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 修改时间
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 订单号
     * @return order_id 订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 订单号
     * @param orderId 订单号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源））
     * @return user_type 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源））
     */
    public int getUserType() {
        return userType;
    }

    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源））
     * @param userType 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源））
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * 交易账号ID
     * @return mt_id 交易账号ID
     */
    public String getMtId() {
        return mtId;
    }

    /**
     * 交易账号ID
     * @param mtId 交易账号ID
     */
    public void setMtId(String mtId) {
        this.mtId = mtId == null ? null : mtId.trim();
    }

    /**
     * 外汇产品
     * @return order_symbol 外汇产品
     */
    public String getOrderSymbol() {
        return orderSymbol;
    }

    /**
     * 外汇产品
     * @param orderSymbol 外汇产品
     */
    public void setOrderSymbol(String orderSymbol) {
        this.orderSymbol = orderSymbol == null ? null : orderSymbol.trim();
    }

    /**
     * 交易手数
     * @return order_lots 交易手数
     */
    public BigDecimal getOrderLots() {
        return orderLots;
    }

    /**
     * 交易手数
     * @param orderLots 交易手数
     */
    public void setOrderLots(BigDecimal orderLots) {
        this.orderLots = orderLots;
    }

    /**
     * 订单类型（ 0 普通订单，1 拆分订单）
     * @return order_type 订单类型（ 0 普通订单，1 拆分订单）
     */
    public int getOrderType() {
        return orderType;
    }

    /**
     * 订单类型（ 0 普通订单，1 拆分订单）
     * @param orderType 订单类型（ 0 普通订单，1 拆分订单）
     */
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    /**
     * 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 已平仓）
     * @return order_state 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 已平仓）
     */
    public int getOrderState() {
        return orderState;
    }

    /**
     * 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 已平仓）
     * @param orderState 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 已平仓）
     */
    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    /**
     * 止损
     * @return order_stoploss 止损
     */
    public BigDecimal getOrderStoploss() {
        return orderStoploss;
    }

    /**
     * 止损
     * @param orderStoploss 止损
     */
    public void setOrderStoploss(BigDecimal orderStoploss) {
        this.orderStoploss = orderStoploss;
    }

    /**
     * 获利
     * @return order_takeprofit 获利
     */
    public BigDecimal getOrderTakeprofit() {
        return orderTakeprofit;
    }

    /**
     * 获利
     * @param orderTakeprofit 获利
     */
    public void setOrderTakeprofit(BigDecimal orderTakeprofit) {
        this.orderTakeprofit = orderTakeprofit;
    }

    /**
     * 原订单号
     * @return order_superior 原订单号
     */
    public String getOrderSuperior() {
        return orderSuperior;
    }

    /**
     * 原订单号
     * @param orderSuperior 原订单号
     */
    public void setOrderSuperior(String orderSuperior) {
        this.orderSuperior = orderSuperior == null ? null : orderSuperior.trim();
    }

    /**
     * 挂单时间
     * @return order_expiration 挂单时间
     */
    public Date getOrderExpiration() {
        return orderExpiration;
    }

    /**
     * 挂单时间
     * @param orderExpiration 挂单时间
     */
    public void setOrderExpiration(Date orderExpiration) {
        this.orderExpiration = orderExpiration;
    }

    /**
     * 交易价格
     * @return order_trade_price 交易价格
     */
    public BigDecimal getOrderTradePrice() {
        return orderTradePrice;
    }

    /**
     * 交易价格
     * @param orderTradePrice 交易价格
     */
    public void setOrderTradePrice(BigDecimal orderTradePrice) {
        this.orderTradePrice = orderTradePrice;
    }

    /**
     * 交易时间
     * @return order_trade_date 交易时间
     */
    public Date getOrderTradeDate() {
        return orderTradeDate;
    }

    /**
     * 交易时间
     * @param orderTradeDate 交易时间
     */
    public void setOrderTradeDate(Date orderTradeDate) {
        this.orderTradeDate = orderTradeDate;
    }

    /**
     * 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     * @return order_trade_operation 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     */
    public int getOrderTradeOperation() {
        return orderTradeOperation;
    }

    /**
     * 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     * @param orderTradeOperation 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     */
    public void setOrderTradeOperation(int orderTradeOperation) {
        this.orderTradeOperation = orderTradeOperation;
    }

    /**
     * 交易类型（0 建仓，1 平仓）
     * @return order_trade_type 交易类型（0 建仓，1 平仓）
     */
    public int getOrderTradeType() {
        return orderTradeType;
    }

    /**
     * 交易类型（0 建仓，1 平仓）
     * @param orderTradeType 交易类型（0 建仓，1 平仓）
     */
    public void setOrderTradeType(int orderTradeType) {
        this.orderTradeType = orderTradeType;
    }

    /**
     * 平仓价格
     * @return order_close_price 平仓价格
     */
    public BigDecimal getOrderClosePrice() {
        return orderClosePrice;
    }

    /**
     * 平仓价格
     * @param orderClosePrice 平仓价格
     */
    public void setOrderClosePrice(BigDecimal orderClosePrice) {
        this.orderClosePrice = orderClosePrice;
    }

    /**
     * 平仓时间
     * @return order_close_date 平仓时间
     */
    public Date getOrderCloseDate() {
        return orderCloseDate;
    }

    /**
     * 平仓时间
     * @param orderCloseDate 平仓时间
     */
    public void setOrderCloseDate(Date orderCloseDate) {
        this.orderCloseDate = orderCloseDate;
    }
}