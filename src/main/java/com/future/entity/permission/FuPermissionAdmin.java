package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuPermissionAdmin {

    public static String USER_ID ="user+id";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员ID
     */
    private Integer userId;

    /**
     * 项目工程KEY
     */
    private Integer projKey;

    /**
     * 管理员状态（0 正常，1冻结，2 删除）
     */
    private Integer adminState;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册日期
     */
    private Date regTime;

    /**
     * 最后登录日期
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
     * 管理员ID
     * @return user_id 管理员ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 管理员ID
     * @param userId 管理员ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 项目工程KEY
     * @return proj_key 项目工程KEY
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * 项目工程KEY
     * @param projKey 项目工程KEY
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * 管理员状态（0 正常，1冻结，2 删除）
     * @return admin_state 管理员状态（0 正常，1冻结，2 删除）
     */
    public Integer getAdminState() {
        return adminState;
    }

    /**
     * 管理员状态（0 正常，1冻结，2 删除）
     * @param adminState 管理员状态（0 正常，1冻结，2 删除）
     */
    public void setAdminState(Integer adminState) {
        this.adminState = adminState;
    }

    /**
     * 邮箱
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
     * 注册日期
     * @return reg_time 注册日期
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * 注册日期
     * @param regTime 注册日期
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * 最后登录日期
     * @return last_login 最后登录日期
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * 最后登录日期
     * @param lastLogin 最后登录日期
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
}