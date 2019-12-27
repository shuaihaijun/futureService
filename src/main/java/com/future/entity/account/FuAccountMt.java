package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

public class FuAccountMt {
    public static String USER_ID="user_id";
    public static String MT_ACC_ID="mt_acc_id";
    public static String IS_SIGNAL="is_signal";
    public static String ACCOUNT_TYPE="account_type";
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
     * 服务器ID
     */
    private Integer serverId;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 经纪人名称
     */
    private String brokerName;

    /**
     * 经纪人ID
     */
    private Integer brokerId;

    /**
     * mt平台账号
     */
    private String mtAccId;

    /**
     * 密码交易
     */
    private String mtPasswordTrade;

    /**
     * 观摩密码
     */
    private String mtPasswordWatch;

    /**
     * mt服务器url
     */
    private String mtAccUrl;

    /**
     * mt服务器port
     */
    private String mtAccPort;

    /**
     * 账户服务器地址
     */
    private String accountUrl;

    /**
     * 账户服务器端口
     */
    private Integer accountPort;

    /**
     * 交易密码是否已校验（0 否，1是）
     */
    private Integer passwordTradeChecked;

    /**
     * 观摩密码是否已校验（0 否，1是）
     */
    private Integer passwordWatchChecked;

    /**
     * 是否为信号源账号(1 是，0 否)
     */
    private Integer isSignal;

    /**
     * 是否为社区账号(1 是，0 否)
     */
    private Integer isChief;

    /**
     * 账户类型：0 实盘账户,1 模拟账户
     */
    private Integer accountType;

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
     * 账户状态（0 正常，1冻结，2 删除）
     */
    private Integer accountState;

    /**
     * MT类型：0 mt4,1 mt5
     */
    private Integer platType;

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
     * 密码交易
     * @return mt_password_trade 密码交易
     */
    public String getMtPasswordTrade() {
        return mtPasswordTrade;
    }

    /**
     * 密码交易
     * @param mtPasswordTrade 密码交易
     */
    public void setMtPasswordTrade(String mtPasswordTrade) {
        this.mtPasswordTrade = mtPasswordTrade == null ? null : mtPasswordTrade.trim();
    }

    /**
     * 观摩密码
     * @return mt_password_watch 观摩密码
     */
    public String getMtPasswordWatch() {
        return mtPasswordWatch;
    }

    /**
     * 观摩密码
     * @param mtPasswordWatch 观摩密码
     */
    public void setMtPasswordWatch(String mtPasswordWatch) {
        this.mtPasswordWatch = mtPasswordWatch == null ? null : mtPasswordWatch.trim();
    }

    /**
     * mt服务器url
     * @return mt_acc_url mt服务器url
     */
    public String getMtAccUrl() {
        return mtAccUrl;
    }

    /**
     * mt服务器url
     * @param mtAccUrl mt服务器url
     */
    public void setMtAccUrl(String mtAccUrl) {
        this.mtAccUrl = mtAccUrl == null ? null : mtAccUrl.trim();
    }

    /**
     * mt服务器port
     * @return mt_acc_port mt服务器port
     */
    public String getMtAccPort() {
        return mtAccPort;
    }

    /**
     * mt服务器port
     * @param mtAccPort mt服务器port
     */
    public void setMtAccPort(String mtAccPort) {
        this.mtAccPort = mtAccPort == null ? null : mtAccPort.trim();
    }

    /**
     * 账户服务器地址
     * @return account_url 账户服务器地址
     */
    public String getAccountUrl() {
        return accountUrl;
    }

    /**
     * 账户服务器地址
     * @param accountUrl 账户服务器地址
     */
    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl == null ? null : accountUrl.trim();
    }

    /**
     * 账户服务器端口
     * @return account_port 账户服务器端口
     */
    public Integer getAccountPort() {
        return accountPort;
    }

    /**
     * 账户服务器端口
     * @param accountPort 账户服务器端口
     */
    public void setAccountPort(Integer accountPort) {
        this.accountPort = accountPort;
    }

    /**
     * 交易密码是否已校验（0 否，1是）
     * @return password_trade_checked 交易密码是否已校验（0 否，1是）
     */
    public Integer getPasswordTradeChecked() {
        return passwordTradeChecked;
    }

    /**
     * 交易密码是否已校验（0 否，1是）
     * @param passwordTradeChecked 交易密码是否已校验（0 否，1是）
     */
    public void setPasswordTradeChecked(Integer passwordTradeChecked) {
        this.passwordTradeChecked = passwordTradeChecked;
    }

    /**
     * 观摩密码是否已校验（0 否，1是）
     * @return password_watch_checked 观摩密码是否已校验（0 否，1是）
     */
    public Integer getPasswordWatchChecked() {
        return passwordWatchChecked;
    }

    /**
     * 观摩密码是否已校验（0 否，1是）
     * @param passwordWatchChecked 观摩密码是否已校验（0 否，1是）
     */
    public void setPasswordWatchChecked(Integer passwordWatchChecked) {
        this.passwordWatchChecked = passwordWatchChecked;
    }

    /**
     * 是否为信号源账号(1 是，0 否)
     * @return is_signal 是否为信号源账号(1 是，0 否)
     */
    public Integer getIsSignal() {
        return isSignal;
    }

    /**
     * 是否为信号源账号(1 是，0 否)
     * @param isSignal 是否为信号源账号(1 是，0 否)
     */
    public void setIsSignal(Integer isSignal) {
        this.isSignal = isSignal;
    }

    /**
     * 是否为社区账号(1 是，0 否)
     * @return is_chief 是否为社区账号(1 是，0 否)
     */
    public Integer getIsChief() {
        return isChief;
    }

    /**
     * 是否为社区账号(1 是，0 否)
     * @param isChief 是否为社区账号(1 是，0 否)
     */
    public void setIsChief(Integer isChief) {
        this.isChief = isChief;
    }

    /**
     * 账户类型：0 实盘账户,1 模拟账户
     * @return account_type 账户类型：0 实盘账户,1 模拟账户
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 账户类型：0 实盘账户,1 模拟账户
     * @param accountType 账户类型：0 实盘账户,1 模拟账户
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
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
     * 账户状态（0 正常，1冻结，2 删除）
     * @return account_state 账户状态（0 正常，1冻结，2 删除）
     */
    public Integer getAccountState() {
        return accountState;
    }

    /**
     * 账户状态（0 正常，1冻结，2 删除）
     * @param accountState 账户状态（0 正常，1冻结，2 删除）
     */
    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    /**
     * MT类型：0 mt4,1 mt5
     * @return plat_type MT类型：0 mt4,1 mt5
     */
    public Integer getPlatType() {
        return platType;
    }

    /**
     * MT类型：0 mt4,1 mt5
     * @param platType MT类型：0 mt4,1 mt5
     */
    public void setPlatType(Integer platType) {
        this.platType = platType;
    }
}