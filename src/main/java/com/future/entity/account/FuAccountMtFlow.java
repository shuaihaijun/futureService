package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountMtFlow {
    public static String USER_ID="user_id";
    public static String MT_ACC_ID="mt_acc_id";
    public static String TRADE_DATE="trade_date";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 交易时间
     */
    private Date tradeDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * mt平台账号
     */
    private String mtAccId;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 信用额
     */
    private BigDecimal credit;

    /**
     * 历史收益
     */
    private BigDecimal profitHistory;

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
     * 入金
     */
    private BigDecimal deposit;

    /**
     * 提取
     */
    private BigDecimal withdraw;

    /**
     * 历史入金
     */
    private BigDecimal depositHistory;

    /**
     * 历史提取
     */
    private BigDecimal withdrawHistory;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
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

    /**
     * 历史收益
     * @return profit_history 历史收益
     */
    public BigDecimal getProfitHistory() {
        return profitHistory;
    }

    /**
     * 历史收益
     * @param profitHistory 历史收益
     */
    public void setProfitHistory(BigDecimal profitHistory) {
        this.profitHistory = profitHistory;
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
     * 入金
     * @return deposit 入金
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * 入金
     * @param deposit 入金
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * 提取
     * @return withdraw 提取
     */
    public BigDecimal getWithdraw() {
        return withdraw;
    }

    /**
     * 提取
     * @param withdraw 提取
     */
    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    /**
     * 历史入金
     * @return deposit_history 历史入金
     */
    public BigDecimal getDepositHistory() {
        return depositHistory;
    }

    /**
     * 历史入金
     * @param depositHistory 历史入金
     */
    public void setDepositHistory(BigDecimal depositHistory) {
        this.depositHistory = depositHistory;
    }

    /**
     * 历史提取
     * @return withdraw_history 历史提取
     */
    public BigDecimal getWithdrawHistory() {
        return withdrawHistory;
    }

    /**
     * 历史提取
     * @param withdrawHistory 历史提取
     */
    public void setWithdrawHistory(BigDecimal withdrawHistory) {
        this.withdrawHistory = withdrawHistory;
    }
}