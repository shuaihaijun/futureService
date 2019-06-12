package com.future.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class FuOrderInfo {
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
     * 订单状态（ 0 交易中 ，1 已建仓，2 部分平仓，3 已平仓）
     */
    private int orderState;

    /**
     * 止损价
     */
    private BigDecimal orderStoploss;

    /**
     * 止盈价
     */
    private BigDecimal orderTakeprofit;

    /**
     * 原订单号
     */
    private String orderSuperior;

    /**
     * 订单来源标识：（0 自交易，1 社区跟单）
     */
    private int orderSourceFlag;

    /**
     * 跟随订单号
     */
    private String orderSourceId;

    /**
     * 跟随mt账号
     */
    private String orderSourceMt;

    /**
     * 订单拆分次数
     */
    private Integer orderSplitCount;

    /**
     * 拆单类型 ( 0 拆单建仓，1 拆单平仓)
     */
    private int orderSplitType;

    /**
     * 盈利
     */
    private BigDecimal orderProfit;

    /**
     * 库存费
     */
    private BigDecimal orderSwap;

    /**
     * 手续费
     */
    private BigDecimal orderCommission;

    /**
     * 魔力值
     */
    private BigDecimal orderMagic;

    /**
     * 订单建仓价格
     */
    private BigDecimal orderOpenPrice;

    /**
     * 订单建仓时间
     */
    private Date orderOpenDate;

    /**
     * 平仓价格
     */
    private BigDecimal orderClosePrice;

    /**
     * 平仓时间
     */
    private Date orderCloseDate;

    /**
     * 挂单时间
     */
    private Date orderExpiration;

    /**
     * 备注
     */
    private String comment;

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
     * 订单状态（ 0 交易中 ，1 已建仓，2 部分平仓，3 已平仓）
     * @return order_state 订单状态（ 0 交易中 ，1 已建仓，2 部分平仓，3 已平仓）
     */
    public int getOrderState() {
        return orderState;
    }

    /**
     * 订单状态（ 0 交易中 ，1 已建仓，2 部分平仓，3 已平仓）
     * @param orderState 订单状态（ 0 交易中 ，1 已建仓，2 部分平仓，3 已平仓）
     */
    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    /**
     * 止损价
     * @return order_stoploss 止损价
     */
    public BigDecimal getOrderStoploss() {
        return orderStoploss;
    }

    /**
     * 止损价
     * @param orderStoploss 止损价
     */
    public void setOrderStoploss(BigDecimal orderStoploss) {
        this.orderStoploss = orderStoploss;
    }

    /**
     * 止盈价
     * @return order_takeprofit 止盈价
     */
    public BigDecimal getOrderTakeprofit() {
        return orderTakeprofit;
    }

    /**
     * 止盈价
     * @param orderTakeprofit 止盈价
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
     * 订单来源标识：（0 自交易，1 社区跟单）
     * @return order_source_flag 订单来源标识：（0 自交易，1 社区跟单）
     */
    public int getOrderSourceFlag() {
        return orderSourceFlag;
    }

    /**
     * 订单来源标识：（0 自交易，1 社区跟单）
     * @param orderSourceFlag 订单来源标识：（0 自交易，1 社区跟单）
     */
    public void setOrderSourceFlag(int orderSourceFlag) {
        this.orderSourceFlag = orderSourceFlag;
    }

    /**
     * 跟随订单号
     * @return order_source_id 跟随订单号
     */
    public String getOrderSourceId() {
        return orderSourceId;
    }

    /**
     * 跟随订单号
     * @param orderSourceId 跟随订单号
     */
    public void setOrderSourceId(String orderSourceId) {
        this.orderSourceId = orderSourceId == null ? null : orderSourceId.trim();
    }

    /**
     * 跟随mt账号
     * @return order_source_mt 跟随mt账号
     */
    public String getOrderSourceMt() {
        return orderSourceMt;
    }

    /**
     * 跟随mt账号
     * @param orderSourceMt 跟随mt账号
     */
    public void setOrderSourceMt(String orderSourceMt) {
        this.orderSourceMt = orderSourceMt == null ? null : orderSourceMt.trim();
    }

    /**
     * 订单拆分次数
     * @return order_split_count 订单拆分次数
     */
    public Integer getOrderSplitCount() {
        return orderSplitCount;
    }

    /**
     * 订单拆分次数
     * @param orderSplitCount 订单拆分次数
     */
    public void setOrderSplitCount(Integer orderSplitCount) {
        this.orderSplitCount = orderSplitCount;
    }

    /**
     * 拆单类型 ( 0 拆单建仓，1 拆单平仓)
     * @return order_split_type 拆单类型 ( 0 拆单建仓，1 拆单平仓)
     */
    public int getOrderSplitType() {
        return orderSplitType;
    }

    /**
     * 拆单类型 ( 0 拆单建仓，1 拆单平仓)
     * @param orderSplitType 拆单类型 ( 0 拆单建仓，1 拆单平仓)
     */
    public void setOrderSplitType(int orderSplitType) {
        this.orderSplitType = orderSplitType;
    }

    /**
     * 盈利
     * @return order_profit 盈利
     */
    public BigDecimal getOrderProfit() {
        return orderProfit;
    }

    /**
     * 盈利
     * @param orderProfit 盈利
     */
    public void setOrderProfit(BigDecimal orderProfit) {
        this.orderProfit = orderProfit;
    }

    /**
     * 库存费
     * @return order_swap 库存费
     */
    public BigDecimal getOrderSwap() {
        return orderSwap;
    }

    /**
     * 库存费
     * @param orderSwap 库存费
     */
    public void setOrderSwap(BigDecimal orderSwap) {
        this.orderSwap = orderSwap;
    }

    /**
     * 手续费
     * @return order_commission 手续费
     */
    public BigDecimal getOrderCommission() {
        return orderCommission;
    }

    /**
     * 手续费
     * @param orderCommission 手续费
     */
    public void setOrderCommission(BigDecimal orderCommission) {
        this.orderCommission = orderCommission;
    }

    /**
     * 魔力值
     * @return order_magic 魔力值
     */
    public BigDecimal getOrderMagic() {
        return orderMagic;
    }

    /**
     * 魔力值
     * @param orderMagic 魔力值
     */
    public void setOrderMagic(BigDecimal orderMagic) {
        this.orderMagic = orderMagic;
    }

    /**
     * 订单建仓价格
     * @return order_open_price 订单建仓价格
     */
    public BigDecimal getOrderOpenPrice() {
        return orderOpenPrice;
    }

    /**
     * 订单建仓价格
     * @param orderOpenPrice 订单建仓价格
     */
    public void setOrderOpenPrice(BigDecimal orderOpenPrice) {
        this.orderOpenPrice = orderOpenPrice;
    }

    /**
     * 订单建仓时间
     * @return order_open_date 订单建仓时间
     */
    public Date getOrderOpenDate() {
        return orderOpenDate;
    }

    /**
     * 订单建仓时间
     * @param orderOpenDate 订单建仓时间
     */
    public void setOrderOpenDate(Date orderOpenDate) {
        this.orderOpenDate = orderOpenDate;
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
     * 备注
     * @return comment 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 备注
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}