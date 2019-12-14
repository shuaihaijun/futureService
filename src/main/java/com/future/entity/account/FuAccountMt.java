package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

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
     * 是否为主账号(1 是，0 否)
     */
    private Integer isChief;

    /**
     * 账户类型：0 mt4,1 mt5
     */
    private Integer accountType;

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
     * 是否为主账号(1 是，0 否)
     * @return is_chief 是否为主账号(1 是，0 否)
     */
    public Integer getIsChief() {
        return isChief;
    }

    /**
     * 是否为主账号(1 是，0 否)
     * @param isChief 是否为主账号(1 是，0 否)
     */
    public void setIsChief(Integer isChief) {
        this.isChief = isChief;
    }

    /**
     * 账户类型：0 mt4,1 mt5
     * @return account_type 账户类型：0 mt4,1 mt5
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 账户类型：0 mt4,1 mt5
     * @param accountType 账户类型：0 mt4,1 mt5
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}