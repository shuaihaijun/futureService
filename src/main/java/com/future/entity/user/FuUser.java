package com.future.entity.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuUser {

    public static final String USER_ID = "id";
    public static final String USER_NAME = "username";
    public static final String USER_TYPE = "user_type";
    public static final String USER_STATE = "user_state";
    public static final String IS_VERIFIED = "is_verified";
    public static final String IS_ACCOUNT = "is_account";
    public static final String INTRODUCER = "introducer";
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer userType;

    /**
     * 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     */
    private Integer userState;

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
    private Integer isVerified;

    /**
     * 是否已绑定第三方账户
     */
    private Integer isAccount;

    /**
     * 介绍人
     */
    private Integer introducer;

    /**
     * 推荐人数
     */
    private Integer recommend;

    /**
     * 性别
     */
    private String sex;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 最大跟单比例
     */
    private BigDecimal followLevel;

    /**
     * 身份证 反面
     */
    private String idObverse;

    /**
     * 身份证 正面
     */
    private String idFront;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 加密盐
     */
    private String salt;

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
     * 用户账号
     * @return username 用户账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户账号
     * @param username 用户账号
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
     * 真实姓名
     * @return real_name 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 真实姓名
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
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
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
     * @return user_type 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
     * @param userType 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     * @return user_state 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     */
    public Integer getUserState() {
        return userState;
    }

    /**
     * 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     * @param userState 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     */
    public void setUserState(Integer userState) {
        this.userState = userState;
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
     * 电话号码
     * @return phone 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话号码
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 手机
     * @return mobile 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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
    public Integer getIsVerified() {
        return isVerified;
    }

    /**
     * 是否已验证身份
     * @param isVerified 是否已验证身份
     */
    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    /**
     * 是否已绑定第三方账户
     * @return is_account 是否已绑定第三方账户
     */
    public Integer getIsAccount() {
        return isAccount;
    }

    /**
     * 是否已绑定第三方账户
     * @param isAccount 是否已绑定第三方账户
     */
    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
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
     * 性别
     * @return sex 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 国家
     * @return country 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 国家
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 省份
     * @return province 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 省份
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 城市
     * @return city 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 地址
     * @return address 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 最大跟单比例
     * @return follow_level 最大跟单比例
     */
    public BigDecimal getFollowLevel() {
        return followLevel;
    }

    /**
     * 最大跟单比例
     * @param followLevel 最大跟单比例
     */
    public void setFollowLevel(BigDecimal followLevel) {
        this.followLevel = followLevel;
    }

    /**
     * 身份证 反面
     * @return id_obverse 身份证 反面
     */
    public String getIdObverse() {
        return idObverse;
    }

    /**
     * 身份证 反面
     * @param idObverse 身份证 反面
     */
    public void setIdObverse(String idObverse) {
        this.idObverse = idObverse == null ? null : idObverse.trim();
    }

    /**
     * 身份证 正面
     * @return id_Front 身份证 正面
     */
    public String getIdFront() {
        return idFront;
    }

    /**
     * 身份证 正面
     * @param idFront 身份证 正面
     */
    public void setIdFront(String idFront) {
        this.idFront = idFront == null ? null : idFront.trim();
    }

    /**
     * 头像地址
     * @return avatar_url 头像地址
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 头像地址
     * @param avatarUrl 头像地址
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * 加密盐
     * @return salt 加密盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 加密盐
     * @param salt 加密盐
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
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