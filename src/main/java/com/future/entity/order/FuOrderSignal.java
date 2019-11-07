package com.future.entity.order;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuOrderSignal {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
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
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户服务器ID
     */
    private Integer mtServerId;

    /**
     * 用户mt平台账号
     */
    private String mtAccId;

    /**
     * 订单号
     */
    private String orderId;

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
    private Integer orderType;

    /**
     * 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 关闭）
     */
    private Integer orderState;

    /**
     * 止损
     */
    private BigDecimal orderStoploss;

    /**
     * 获利
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
     * 原订单号
     */
    private String orderSuperior;

    /**
     * 挂单时间
     */
    private Date orderExpiration;

    /**
     * 魔法数字
     */
    private BigDecimal orderMagic;

    /**
     * 交易价格
     */
    private BigDecimal orderOpenPrice;

    /**
     * 交易时间
     */
    private Date orderOpenDate;

    /**
     * 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     */
    private Integer orderTradeOperation;

    /**
     * 交易类型（0 建仓，1 平仓）
     */
    private Integer orderTradeType;

    /**
     * 平仓价格
     */
    private BigDecimal orderClosePrice;

    /**
     * 平仓时间
     */
    private Date orderCloseDate;

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
     * 用户服务器ID
     * @return mt_server_id 用户服务器ID
     */
    public Integer getMtServerId() {
        return mtServerId;
    }

    /**
     * 用户服务器ID
     * @param mtServerId 用户服务器ID
     */
    public void setMtServerId(Integer mtServerId) {
        this.mtServerId = mtServerId;
    }

    /**
     * 用户mt平台账号
     * @return mt_acc_id 用户mt平台账号
     */
    public String getMtAccId() {
        return mtAccId;
    }

    /**
     * 用户mt平台账号
     * @param mtAccId 用户mt平台账号
     */
    public void setMtAccId(String mtAccId) {
        this.mtAccId = mtAccId == null ? null : mtAccId.trim();
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
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 订单类型（ 0 普通订单，1 拆分订单）
     * @param orderType 订单类型（ 0 普通订单，1 拆分订单）
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 关闭）
     * @return order_state 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 关闭）
     */
    public Integer getOrderState() {
        return orderState;
    }

    /**
     * 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 关闭）
     * @param orderState 订单状态（ 0 交易中 ，1 已交易，2 部分平仓，3 关闭）
     */
    public void setOrderState(Integer orderState) {
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
     * @return order_profit 获利
     */
    public BigDecimal getOrderProfit() {
        return orderProfit;
    }

    /**
     * 获利
     * @param orderProfit 获利
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
     * 魔法数字
     * @return order_magic 魔法数字
     */
    public BigDecimal getOrderMagic() {
        return orderMagic;
    }

    /**
     * 魔法数字
     * @param orderMagic 魔法数字
     */
    public void setOrderMagic(BigDecimal orderMagic) {
        this.orderMagic = orderMagic;
    }

    /**
     * 交易价格
     * @return order_open_price 交易价格
     */
    public BigDecimal getOrderOpenPrice() {
        return orderOpenPrice;
    }

    /**
     * 交易价格
     * @param orderOpenPrice 交易价格
     */
    public void setOrderOpenPrice(BigDecimal orderOpenPrice) {
        this.orderOpenPrice = orderOpenPrice;
    }

    /**
     * 交易时间
     * @return order_open_date 交易时间
     */
    public Date getOrderOpenDate() {
        return orderOpenDate;
    }

    /**
     * 交易时间
     * @param orderOpenDate 交易时间
     */
    public void setOrderOpenDate(Date orderOpenDate) {
        this.orderOpenDate = orderOpenDate;
    }

    /**
     * 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     * @return order_trade_operation 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     */
    public Integer getOrderTradeOperation() {
        return orderTradeOperation;
    }

    /**
     * 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     * @param orderTradeOperation 交易类别（0 buy，1 sell, 2 buylimit ,3 selllimit, 4 buystop, 5 sellstop,6 deposit, 7 credit，99 close）
     */
    public void setOrderTradeOperation(Integer orderTradeOperation) {
        this.orderTradeOperation = orderTradeOperation;
    }

    /**
     * 交易类型（0 建仓，1 平仓）
     * @return order_trade_type 交易类型（0 建仓，1 平仓）
     */
    public Integer getOrderTradeType() {
        return orderTradeType;
    }

    /**
     * 交易类型（0 建仓，1 平仓）
     * @param orderTradeType 交易类型（0 建仓，1 平仓）
     */
    public void setOrderTradeType(Integer orderTradeType) {
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