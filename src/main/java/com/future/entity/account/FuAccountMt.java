package com.future.entity.account;

public class FuAccountMt {
    /**
     * 
     */
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
    private String accountId;

    /**
     * 密码交易
     */
    private String passwordTrade;

    /**
     * 观摩密码
     */
    private String passwordWatch;

    /**
     * 交易密码是否已校验（0 否，1是）
     */
    private int passwordTradeChecked;

    /**
     * 观摩密码是否已校验（0 否，1是）
     */
    private int passwordWatchChecked;

    /**
     * 是否为主账号(1 是，0 否)
     */
    private int isChief;

    /**
     * 账户类型：0 mt4,1 mt5
     */
    private int accountType;

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
     * @return account_id mt平台账号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * mt平台账号
     * @param accountId mt平台账号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * 密码交易
     * @return password_trade 密码交易
     */
    public String getPasswordTrade() {
        return passwordTrade;
    }

    /**
     * 密码交易
     * @param passwordTrade 密码交易
     */
    public void setPasswordTrade(String passwordTrade) {
        this.passwordTrade = passwordTrade == null ? null : passwordTrade.trim();
    }

    /**
     * 观摩密码
     * @return password_watch 观摩密码
     */
    public String getPasswordWatch() {
        return passwordWatch;
    }

    /**
     * 观摩密码
     * @param passwordWatch 观摩密码
     */
    public void setPasswordWatch(String passwordWatch) {
        this.passwordWatch = passwordWatch == null ? null : passwordWatch.trim();
    }

    /**
     * 交易密码是否已校验（0 否，1是）
     * @return password_trade_checked 交易密码是否已校验（0 否，1是）
     */
    public int getPasswordTradeChecked() {
        return passwordTradeChecked;
    }

    /**
     * 交易密码是否已校验（0 否，1是）
     * @param passwordTradeChecked 交易密码是否已校验（0 否，1是）
     */
    public void setPasswordTradeChecked(int passwordTradeChecked) {
        this.passwordTradeChecked = passwordTradeChecked;
    }

    /**
     * 观摩密码是否已校验（0 否，1是）
     * @return password_watch_checked 观摩密码是否已校验（0 否，1是）
     */
    public int getPasswordWatchChecked() {
        return passwordWatchChecked;
    }

    /**
     * 观摩密码是否已校验（0 否，1是）
     * @param passwordWatchChecked 观摩密码是否已校验（0 否，1是）
     */
    public void setPasswordWatchChecked(int passwordWatchChecked) {
        this.passwordWatchChecked = passwordWatchChecked;
    }

    /**
     * 是否为主账号(1 是，0 否)
     * @return is_chief 是否为主账号(1 是，0 否)
     */
    public int getIsChief() {
        return isChief;
    }

    /**
     * 是否为主账号(1 是，0 否)
     * @param isChief 是否为主账号(1 是，0 否)
     */
    public void setIsChief(int isChief) {
        this.isChief = isChief;
    }

    /**
     * 账户类型：0 mt4,1 mt5
     * @return account_type 账户类型：0 mt4,1 mt5
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * 账户类型：0 mt4,1 mt5
     * @param accountType 账户类型：0 mt4,1 mt5
     */
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}