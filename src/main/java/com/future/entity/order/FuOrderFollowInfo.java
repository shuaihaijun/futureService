package com.future.entity.order;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuOrderFollowInfo {

    public static final String USER_ID = "user_id";
    public static final String SIGNAL_ID = "signal_id";
    public static final String ORDER_ID = "order_id";
    public static final String SIGNAL_ORDER_ID = "signal_order_id";
    public static final String ORDER_SYMBOL = "order_symbol";
    public static final String ORDER_TYPE = "order_type";
    public static final String ORDER_DIRECT = "follow_direct";
    public static final String ORDER_OPEN_DATE = "order_open_date";
    public static final String ORDER_CLOSE_DATE = "order_close_date";
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
     * 订单号
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 信号源ID
     */
    private Integer signalId;

    /**
     * 交易账号ID
     */
    private String userMtAccId;

    /**
     * 用户服务器ID
     */
    private Integer userServerId;

    /**
     * 用户mt服务器名称
     */
    private String userServerName;

    /**
     * 信号源服务器ID
     */
    private Integer signalServerId;

    /**
     * 信号源mt服务器名称
     */
    private String signalServerName;

    /**
     * 信号源mt平台账号
     */
    private String signalMtAccId;

    /**
     * 信号源订单号
     */
    private String signalOrderId;

    /**
     * 外汇产品
     */
    private String orderSymbol;

    /**
     * 交易手数
     */
    private BigDecimal orderLots;

    /**
     * 止损价
     */
    private BigDecimal orderStoploss;

    /**
     * 止盈价
     */
    private BigDecimal orderTakeprofit;

    /**
     * 订单来源标识：（0 自交易，1 社区跟单）
     */
    private Integer orderSourceFlag;

    /**
     * 订单交易操作（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill）
     */
    private Integer orderTradeOperation;

    /**
     * 订单交易类型（ OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5）
     */
    private Integer orderType;

    /**
     * 拆单类型 ( 0 正常，1拆单平仓，2拆单键仓)
     */
    private Integer orderSplitType;

    /**
     * 原在仓订单号
     */
    private String orderSuperior;

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
     * 跟单方向（0 正向跟单，1  反向跟单）
     */
    private Integer followDirect;

    /**
     * 跟单模式（0 多空跟单，1 只跟多单，2 只跟空单）
     */
    private Integer followMode;

    /**
     * 跟单类型（0 按手数比例，1 按固定金额，2 按固定手数）
     */
    private Integer followType;

    /**
     * 跟单数量
     */
    private BigDecimal followAmount;

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
     * 信号源ID
     * @return signal_id 信号源ID
     */
    public Integer getSignalId() {
        return signalId;
    }

    /**
     * 信号源ID
     * @param signalId 信号源ID
     */
    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    /**
     * 交易账号ID
     * @return user_mt_acc_id 交易账号ID
     */
    public String getUserMtAccId() {
        return userMtAccId;
    }

    /**
     * 交易账号ID
     * @param userMtAccId 交易账号ID
     */
    public void setUserMtAccId(String userMtAccId) {
        this.userMtAccId = userMtAccId == null ? null : userMtAccId.trim();
    }

    /**
     * 用户服务器ID
     * @return user_server_id 用户服务器ID
     */
    public Integer getUserServerId() {
        return userServerId;
    }

    /**
     * 用户服务器ID
     * @param userServerId 用户服务器ID
     */
    public void setUserServerId(Integer userServerId) {
        this.userServerId = userServerId;
    }

    /**
     * 用户mt服务器名称
     * @return user_server_name 用户mt服务器名称
     */
    public String getUserServerName() {
        return userServerName;
    }

    /**
     * 用户mt服务器名称
     * @param userServerName 用户mt服务器名称
     */
    public void setUserServerName(String userServerName) {
        this.userServerName = userServerName == null ? null : userServerName.trim();
    }

    /**
     * 信号源服务器ID
     * @return signal_server_id 信号源服务器ID
     */
    public Integer getSignalServerId() {
        return signalServerId;
    }

    /**
     * 信号源服务器ID
     * @param signalServerId 信号源服务器ID
     */
    public void setSignalServerId(Integer signalServerId) {
        this.signalServerId = signalServerId;
    }

    /**
     * 信号源mt服务器名称
     * @return signal_server_name 信号源mt服务器名称
     */
    public String getSignalServerName() {
        return signalServerName;
    }

    /**
     * 信号源mt服务器名称
     * @param signalServerName 信号源mt服务器名称
     */
    public void setSignalServerName(String signalServerName) {
        this.signalServerName = signalServerName == null ? null : signalServerName.trim();
    }

    /**
     * 信号源mt平台账号
     * @return signal_mt_acc_id 信号源mt平台账号
     */
    public String getSignalMtAccId() {
        return signalMtAccId;
    }

    /**
     * 信号源mt平台账号
     * @param signalMtAccId 信号源mt平台账号
     */
    public void setSignalMtAccId(String signalMtAccId) {
        this.signalMtAccId = signalMtAccId == null ? null : signalMtAccId.trim();
    }

    /**
     * 信号源订单号
     * @return signal_order_id 信号源订单号
     */
    public String getSignalOrderId() {
        return signalOrderId;
    }

    /**
     * 信号源订单号
     * @param signalOrderId 信号源订单号
     */
    public void setSignalOrderId(String signalOrderId) {
        this.signalOrderId = signalOrderId == null ? null : signalOrderId.trim();
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
     * 订单来源标识：（0 自交易，1 社区跟单）
     * @return order_source_flag 订单来源标识：（0 自交易，1 社区跟单）
     */
    public Integer getOrderSourceFlag() {
        return orderSourceFlag;
    }

    /**
     * 订单来源标识：（0 自交易，1 社区跟单）
     * @param orderSourceFlag 订单来源标识：（0 自交易，1 社区跟单）
     */
    public void setOrderSourceFlag(Integer orderSourceFlag) {
        this.orderSourceFlag = orderSourceFlag;
    }

    /**
     * 订单交易操作（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill）
     * @return order_trade_operation 订单交易操作（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill）
     */
    public Integer getOrderTradeOperation() {
        return orderTradeOperation;
    }

    /**
     * 订单交易操作（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill）
     * @param orderTradeOperation 订单交易操作（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill）
     */
    public void setOrderTradeOperation(Integer orderTradeOperation) {
        this.orderTradeOperation = orderTradeOperation;
    }

    /**
     * 订单交易类型（ OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5）
     * @return order_type 订单交易类型（ OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5）
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 订单交易类型（ OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5）
     * @param orderType 订单交易类型（ OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5）
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 拆单类型 ( 0 正常，1拆单平仓，2拆单键仓)
     * @return order_split_type 拆单类型 ( 0 正常，1拆单平仓，2拆单键仓)
     */
    public Integer getOrderSplitType() {
        return orderSplitType;
    }

    /**
     * 拆单类型 ( 0 正常，1拆单平仓，2拆单键仓)
     * @param orderSplitType 拆单类型 ( 0 正常，1拆单平仓，2拆单键仓)
     */
    public void setOrderSplitType(Integer orderSplitType) {
        this.orderSplitType = orderSplitType;
    }

    /**
     * 原在仓订单号
     * @return order_superior 原在仓订单号
     */
    public String getOrderSuperior() {
        return orderSuperior;
    }

    /**
     * 原在仓订单号
     * @param orderSuperior 原在仓订单号
     */
    public void setOrderSuperior(String orderSuperior) {
        this.orderSuperior = orderSuperior == null ? null : orderSuperior.trim();
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
     * 跟单方向（0 正向跟单，1  反向跟单）
     * @return follow_direct 跟单方向（0 正向跟单，1  反向跟单）
     */
    public Integer getFollowDirect() {
        return followDirect;
    }

    /**
     * 跟单方向（0 正向跟单，1  反向跟单）
     * @param followDirect 跟单方向（0 正向跟单，1  反向跟单）
     */
    public void setFollowDirect(Integer followDirect) {
        this.followDirect = followDirect;
    }

    /**
     * 跟单模式（0 多空跟单，1 只跟多单，2 只跟空单）
     * @return follow_mode 跟单模式（0 多空跟单，1 只跟多单，2 只跟空单）
     */
    public Integer getFollowMode() {
        return followMode;
    }

    /**
     * 跟单模式（0 多空跟单，1 只跟多单，2 只跟空单）
     * @param followMode 跟单模式（0 多空跟单，1 只跟多单，2 只跟空单）
     */
    public void setFollowMode(Integer followMode) {
        this.followMode = followMode;
    }

    /**
     * 跟单类型（0 按手数比例，1 按固定金额，2 按固定手数）
     * @return follow_type 跟单类型（0 按手数比例，1 按固定金额，2 按固定手数）
     */
    public Integer getFollowType() {
        return followType;
    }

    /**
     * 跟单类型（0 按手数比例，1 按固定金额，2 按固定手数）
     * @param followType 跟单类型（0 按手数比例，1 按固定金额，2 按固定手数）
     */
    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    /**
     * 跟单数量
     * @return follow_amount 跟单数量
     */
    public BigDecimal getFollowAmount() {
        return followAmount;
    }

    /**
     * 跟单数量
     * @param followAmount 跟单数量
     */
    public void setFollowAmount(BigDecimal followAmount) {
        this.followAmount = followAmount;
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