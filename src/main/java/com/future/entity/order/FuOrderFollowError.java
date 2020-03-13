package com.future.entity.order;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuOrderFollowError {

    public static final String USER_ID = "user_id";
    public static final String USER_MT_ACC_ID = "user_mt_acc_id";
    public static final String USER_SERVER_NAME = "user_server_name";
    public static final String SIGNAL_ID = "signal_id";
    public static final String SIGNAL_MT_ACC_ID = "signal_mt_acc_id";
    public static final String SIGNAL_SERVER_NAME = "signal_server_name";

    public static final String SIGNAL_ORDER_ID = "signal_order_id";
    public static final String ORDER_SYMBOL = "order_symbol";
    public static final String ORDER_TYPE = "order_type";
    public static final String FOLLOW_DIRECT = "follow_direct";
    public static final String ORDER_OPEN_DATE = "order_open_date";
    /**
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
     * 跟随订单号
     */
    private Integer userOrderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户服务器ID
     */
    private Integer userServerId;

    /**
     * 用户mt服务器名称
     */
    private String userServerName;

    /**
     * 用户mt平台账号
     */
    private String userMtAccId;

    /**
     * 信号源ID
     */
    private Integer signalId;

    /**
     * 信号源订单号
     */
    private String signalOrderId;

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
     * 原信号源单号
     */
    private String signalOrderSuperior;

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
     * 跟单系数
     */
    private BigDecimal followAmount;

    /**
     * 订单手数
     */
    private BigDecimal orderLots;

    /**
     * 外汇产品
     */
    private String orderSymbol;

    /**
     * 订单交易类型（ OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5）
     */
    private Integer orderType;

    /**
     * 操作类型（0 OPEN ，1 CLOSE，2 MODIFY，3 CLOSE_PARTIAL，4 CLOSE_MAGIC）
     */
    private Integer orderTradeOperation;

    /**
     * 交易价格
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
     * 订单错误编码
     */
    private Integer errorCode;

    /**
     * 订单错误信息
     */
    private String errorMsg;

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
     * 跟随订单号
     * @return user_order_id 跟随订单号
     */
    public Integer getUserOrderId() {
        return userOrderId;
    }

    /**
     * 跟随订单号
     * @param userOrderId 跟随订单号
     */
    public void setUserOrderId(Integer userOrderId) {
        this.userOrderId = userOrderId;
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
     * 用户mt平台账号
     * @return user_mt_acc_id 用户mt平台账号
     */
    public String getUserMtAccId() {
        return userMtAccId;
    }

    /**
     * 用户mt平台账号
     * @param userMtAccId 用户mt平台账号
     */
    public void setUserMtAccId(String userMtAccId) {
        this.userMtAccId = userMtAccId == null ? null : userMtAccId.trim();
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
     * 原信号源单号
     * @return signal_order_superior 原信号源单号
     */
    public String getSignalOrderSuperior() {
        return signalOrderSuperior;
    }

    /**
     * 原信号源单号
     * @param signalOrderSuperior 原信号源单号
     */
    public void setSignalOrderSuperior(String signalOrderSuperior) {
        this.signalOrderSuperior = signalOrderSuperior == null ? null : signalOrderSuperior.trim();
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
     * 跟单系数
     * @return follow_amount 跟单系数
     */
    public BigDecimal getFollowAmount() {
        return followAmount;
    }

    /**
     * 跟单系数
     * @param followAmount 跟单系数
     */
    public void setFollowAmount(BigDecimal followAmount) {
        this.followAmount = followAmount;
    }

    /**
     * 订单手数
     * @return order_lots 订单手数
     */
    public BigDecimal getOrderLots() {
        return orderLots;
    }

    /**
     * 订单手数
     * @param orderLots 订单手数
     */
    public void setOrderLots(BigDecimal orderLots) {
        this.orderLots = orderLots;
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
     * 操作类型（0 OPEN ，1 CLOSE，2 MODIFY，3 CLOSE_PARTIAL，4 CLOSE_MAGIC）
     * @return order_trade_operation 操作类型（0 OPEN ，1 CLOSE，2 MODIFY，3 CLOSE_PARTIAL，4 CLOSE_MAGIC）
     */
    public Integer getOrderTradeOperation() {
        return orderTradeOperation;
    }

    /**
     * 操作类型（0 OPEN ，1 CLOSE，2 MODIFY，3 CLOSE_PARTIAL，4 CLOSE_MAGIC）
     * @param orderTradeOperation 操作类型（0 OPEN ，1 CLOSE，2 MODIFY，3 CLOSE_PARTIAL，4 CLOSE_MAGIC）
     */
    public void setOrderTradeOperation(Integer orderTradeOperation) {
        this.orderTradeOperation = orderTradeOperation;
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
     * 订单错误编码
     * @return error_code 订单错误编码
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 订单错误编码
     * @param errorCode 订单错误编码
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 订单错误信息
     * @return error_msg 订单错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 订单错误信息
     * @param errorMsg 订单错误信息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }
}