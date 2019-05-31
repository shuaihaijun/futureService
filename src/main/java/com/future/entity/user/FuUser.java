package com.future.entity.user;

import java.util.Date;

public class FuUser {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 最后登录时间
     */
    private Date lastLogin;

    /**
     * 登录地址
     */
    private String loginIp;

    /**
     * 注册地址
     */
    private String regIp;

    /**
     * 是否已验证身份
     */
    private Byte isVerified;

    /**
     * 是否已绑定第三方账户
     */
    private Byte isAccount;

    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源）
     */
    private Byte userType;

    /**
     * 用户状态(0 未审核，1 正常，2 冻结，2 删除）
     */
    private Byte userState;

    /**
     * 介绍人
     */
    private Integer introducer;

    /**
     * 推荐人数
     */
    private Integer recommend;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

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
     * 用户名称
     * @return username 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名称
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 昵称
     * @return ref_name 昵称
     */
    public String getRefName() {
        return refName;
    }

    /**
     * 昵称
     * @param refName 昵称
     */
    public void setRefName(String refName) {
        this.refName = refName == null ? null : refName.trim();
    }

    /**
     * 电子邮件
     * @return email 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮件
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 注册时间
     * @return reg_time 注册时间
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * 注册时间
     * @param regTime 注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * 最后登录时间
     * @return last_login 最后登录时间
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * 最后登录时间
     * @param lastLogin 最后登录时间
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * 登录地址
     * @return login_ip 登录地址
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 登录地址
     * @param loginIp 登录地址
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * 注册地址
     * @return reg_ip 注册地址
     */
    public String getRegIp() {
        return regIp;
    }

    /**
     * 注册地址
     * @param regIp 注册地址
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp == null ? null : regIp.trim();
    }

    /**
     * 是否已验证身份
     * @return is_verified 是否已验证身份
     */
    public Byte getIsVerified() {
        return isVerified;
    }

    /**
     * 是否已验证身份
     * @param isVerified 是否已验证身份
     */
    public void setIsVerified(Byte isVerified) {
        this.isVerified = isVerified;
    }

    /**
     * 是否已绑定第三方账户
     * @return is_account 是否已绑定第三方账户
     */
    public Byte getIsAccount() {
        return isAccount;
    }

    /**
     * 是否已绑定第三方账户
     * @param isAccount 是否已绑定第三方账户
     */
    public void setIsAccount(Byte isAccount) {
        this.isAccount = isAccount;
    }

    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源）
     * @return user_type 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源）
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源）
     * @param userType 用户类型（总经理、总监、业务员、PIB、MIB、IB、VIP会员、普通会员、试用会员、分析师、信号源）
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    /**
     * 用户状态(0 未审核，1 正常，2 冻结，2 删除）
     * @return user_state 用户状态(0 未审核，1 正常，2 冻结，2 删除）
     */
    public Byte getUserState() {
        return userState;
    }

    /**
     * 用户状态(0 未审核，1 正常，2 冻结，2 删除）
     * @param userState 用户状态(0 未审核，1 正常，2 冻结，2 删除）
     */
    public void setUserState(Byte userState) {
        this.userState = userState;
    }

    /**
     * 介绍人
     * @return introducer 介绍人
     */
    public Integer getIntroducer() {
        return introducer;
    }

    /**
     * 介绍人
     * @param introducer 介绍人
     */
    public void setIntroducer(Integer introducer) {
        this.introducer = introducer;
    }

    /**
     * 推荐人数
     * @return recommend 推荐人数
     */
    public Integer getRecommend() {
        return recommend;
    }

    /**
     * 推荐人数
     * @param recommend 推荐人数
     */
    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
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
}