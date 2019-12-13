package com.future.pojo.bo.order;

/*用户MT账户BO*/
public class UserMTAccountBO {

    private Integer accountId;//账户id
    private Integer userId;//用户id
    private Integer serverId;//服务器ID
    private Integer brokerId;//经纪人ID
    private String brokerName;//代理名称
    private int serverState;//服务器状态
    private String serverName;//服务器名称
    private String mtAccId;//mt平台账号
    private String mtPasswordTrade;//交易密码
    private String mtPasswordWatch;//观摩密码
    private int passwordTradeChecked;//交易密码是否已校验（0 否，1是
    private int passwordWatchChecked;//观摩密码是否已校验（0 否，1是
    private int accountType;//账户类型：0 mt4,1 mt5
    private int isChief;//是否为主账号
    private String accountUrl;//账户所在位置URL
    private Integer accountPort;//账户所在位置PORT

    /**
     * 用户账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 昵称
     */
    private String refName;
    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源）
     */
    private int userType;
    /**
     * 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     */
    private int userState;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;

    public Integer getAccountId() {
      return accountId;
    }

    public void setAccountId(Integer accountId) {
      this.accountId = accountId;
    }

    public Integer getUserId() {
      return userId;
    }

    public void setUserId(Integer userId) {
      this.userId = userId;
    }

    public Integer getServerId() {
      return serverId;
    }

    public void setServerId(Integer serverId) {
      this.serverId = serverId;
    }

    public Integer getBrokerId() {
      return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
      this.brokerId = brokerId;
    }

    public String getServerName() {
      return serverName;
    }

    public void setServerName(String serverName) {
      this.serverName = serverName;
    }

    public String getMtAccId() {
      return mtAccId;
    }

    public void setMtAccId(String mtAccId) {
      this.mtAccId = mtAccId;
    }

    public String getMtPasswordTrade() {
      return mtPasswordTrade;
    }

    public void setMtPasswordTrade(String mtPasswordTrade) {
      this.mtPasswordTrade = mtPasswordTrade;
    }

    public String getMtPasswordWatch() {
      return mtPasswordWatch;
    }

    public void setMtPasswordWatch(String mtPasswordWatch) {
      this.mtPasswordWatch = mtPasswordWatch;
    }

    public int getPasswordTradeChecked() {
      return passwordTradeChecked;
    }

    public void setPasswordTradeChecked(int passwordTradeChecked) {
      this.passwordTradeChecked = passwordTradeChecked;
    }

    public int getAccountType() {
      return accountType;
    }

    public void setAccountType(int accountType) {
      this.accountType = accountType;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }


    public String getRealName() {
      return realName;
    }

    public void setRealName(String realName) {
      this.realName = realName;
    }

    public String getRefName() {
      return refName;
    }

    public void setRefName(String refName) {
      this.refName = refName;
    }

    public int getUserType() {
      return userType;
    }

    public void setUserType(int userType) {
      this.userType = userType;
    }

    public int getUserState() {
      return userState;
    }

    public void setUserState(int userState) {
      this.userState = userState;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public int getServerState() {
        return serverState;
    }

    public void setServerState(int serverState) {
        this.serverState = serverState;
    }

    public int getIsChief() {
        return isChief;
    }

    public void setIsChief(int isChief) {
        this.isChief = isChief;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public Integer getAccountPort() {
        return accountPort;
    }

    public void setAccountPort(Integer accountPort) {
        this.accountPort = accountPort;
    }

    public int getPasswordWatchChecked() {
        return passwordWatchChecked;
    }

    public void setPasswordWatchChecked(int passwordWatchChecked) {
        this.passwordWatchChecked = passwordWatchChecked;
    }
}
