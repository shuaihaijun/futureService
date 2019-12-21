package com.future.entity.order;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuOrderDaysum {


    public static String USER_ID="user_id";
    public static String USER_TYPE="user_type";
    public static String OTHER_ID="other_id";
    public static String TRADE_DATE="tradeDate";

    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer userType;

    /**
     * signalID/agentId
     */
    private Integer otherId;

    /**
     * 服务器ID
     */
    private Integer serverId;

    /**
     * 经纪人ID
     */
    private Integer brokerId;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 经纪人名称
     */
    private String brokerName;

    /**
     * mt平台账号
     */
    private String mtAccId;

    /**
     * 交易时间
     */
    private Date tradeDate;

    /**
     * 订单交易类型（0 社区跟单，1 自交易）
     */
    private Integer tradeType;

    /**
     * 交易手数
     */
    private BigDecimal orderLots;

    /**
     * 交易笔数
     */
    private Integer orderNum;

    /**
     * 交易赢笔数
     */
    private Integer orderWinNum;

    /**
     * 交易金额
     */
    private BigDecimal orderMoney;

    /**
     * 手续费
     */
    private BigDecimal orderCommission;

    /**
     * 库存费
     */
    private BigDecimal orderSwap;

    /**
     * 社区交易天数
     */
    private Integer tradeWeeks;

    /**
     * 跟随人数
     */
    private Integer follows;

    /**
     * 收益
     */
    private BigDecimal profit;

    /**
     * 净值
     */
    private BigDecimal equity;

    /**
     * 保证金
     */
    private BigDecimal margin;

    /**
     * 杠杆
     */
    private BigDecimal leverage;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 信用额
     */
    private BigDecimal credit;

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
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     * @return user_type 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     * @param userType 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * signalID/agentId
     * @return other_id signalID/agentId
     */
    public Integer getOtherId() {
        return otherId;
    }

    /**
     * signalID/agentId
     * @param otherId signalID/agentId
     */
    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    /**
     * 服务器ID
     * @return server_id 服务器ID
     */
    public Integer getServerId() {
        return serverId;
    }

    /**
     * 服务器ID
     * @param serverId 服务器ID
     */
    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    /**
     * 经纪人ID
     * @return broker_id 经纪人ID
     */
    public Integer getBrokerId() {
        return brokerId;
    }

    /**
     * 经纪人ID
     * @param brokerId 经纪人ID
     */
    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    /**
     * 服务器名称
     * @return server_name 服务器名称
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * 服务器名称
     * @param serverName 服务器名称
     */
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    /**
     * 经纪人名称
     * @return broker_name 经纪人名称
     */
    public String getBrokerName() {
        return brokerName;
    }

    /**
     * 经纪人名称
     * @param brokerName 经纪人名称
     */
    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName == null ? null : brokerName.trim();
    }

    /**
     * mt平台账号
     * @return mt_acc_id mt平台账号
     */
    public String getMtAccId() {
        return mtAccId;
    }

    /**
     * mt平台账号
     * @param mtAccId mt平台账号
     */
    public void setMtAccId(String mtAccId) {
        this.mtAccId = mtAccId == null ? null : mtAccId.trim();
    }

    /**
     * 交易时间
     * @return trade_date 交易时间
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     * 交易时间
     * @param tradeDate 交易时间
     */
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * 订单交易类型（0 社区跟单，1 自交易）
     * @return trade_type 订单交易类型（0 社区跟单，1 自交易）
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     * 订单交易类型（0 社区跟单，1 自交易）
     * @param tradeType 订单交易类型（0 社区跟单，1 自交易）
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
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
     * 交易笔数
     * @return order_num 交易笔数
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 交易笔数
     * @param orderNum 交易笔数
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 交易赢笔数
     * @return order_win_num 交易赢笔数
     */
    public Integer getOrderWinNum() {
        return orderWinNum;
    }

    /**
     * 交易赢笔数
     * @param orderWinNum 交易赢笔数
     */
    public void setOrderWinNum(Integer orderWinNum) {
        this.orderWinNum = orderWinNum;
    }

    /**
     * 交易金额
     * @return order_money 交易金额
     */
    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    /**
     * 交易金额
     * @param orderMoney 交易金额
     */
    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
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
     * 社区交易天数
     * @return trade_weeks 社区交易天数
     */
    public Integer getTradeWeeks() {
        return tradeWeeks;
    }

    /**
     * 社区交易天数
     * @param tradeWeeks 社区交易天数
     */
    public void setTradeWeeks(Integer tradeWeeks) {
        this.tradeWeeks = tradeWeeks;
    }

    /**
     * 跟随人数
     * @return follows 跟随人数
     */
    public Integer getFollows() {
        return follows;
    }

    /**
     * 跟随人数
     * @param follows 跟随人数
     */
    public void setFollows(Integer follows) {
        this.follows = follows;
    }

    /**
     * 收益
     * @return profit 收益
     */
    public BigDecimal getProfit() {
        return profit;
    }

    /**
     * 收益
     * @param profit 收益
     */
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * 净值
     * @return equity 净值
     */
    public BigDecimal getEquity() {
        return equity;
    }

    /**
     * 净值
     * @param equity 净值
     */
    public void setEquity(BigDecimal equity) {
        this.equity = equity;
    }

    /**
     * 保证金
     * @return margin 保证金
     */
    public BigDecimal getMargin() {
        return margin;
    }

    /**
     * 保证金
     * @param margin 保证金
     */
    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    /**
     * 杠杆
     * @return leverage 杠杆
     */
    public BigDecimal getLeverage() {
        return leverage;
    }

    /**
     * 杠杆
     * @param leverage 杠杆
     */
    public void setLeverage(BigDecimal leverage) {
        this.leverage = leverage;
    }

    /**
     * 余额
     * @return balance 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 余额
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 信用额
     * @return credit 信用额
     */
    public BigDecimal getCredit() {
        return credit;
    }

    /**
     * 信用额
     * @param credit 信用额
     */
    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}