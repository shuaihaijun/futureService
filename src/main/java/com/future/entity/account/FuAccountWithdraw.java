package com.future.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountWithdraw {
    /**
     * 
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账户ID
     */
    private Integer accountId;

    /**
     * 回撤金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 银行名称 
     */
    private String bankName;

    /**
     * 银行code
     */
    private String bankCode;

    /**
     * 区域
     */
    private String bankArea;

    /**
     * 操作前余额
     */
    private BigDecimal accountBefore;

    /**
     * 操作后余额
     */
    private BigDecimal accountAfter;

    /**
     * 回撤时间
     */
    private Date withdrawTime;

    /**
     * 人民币
     */
    private BigDecimal withdrawRmb;

    /**
     * 回撤状态（0 成功，1 失败，2 执行中）
     */
    private Byte withdrawState;

    /**
     * 备注
     */
    private String comment;

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
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 账户ID
     * @return account_id 账户ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 账户ID
     * @param accountId 账户ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 回撤金额
     * @return withdraw_amount 回撤金额
     */
    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    /**
     * 回撤金额
     * @param withdrawAmount 回撤金额
     */
    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    /**
     * 银行名称 
     * @return bank_name 银行名称 
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 银行名称 
     * @param bankName 银行名称 
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 银行code
     * @return bank_code 银行code
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 银行code
     * @param bankCode 银行code
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    /**
     * 区域
     * @return bank_area 区域
     */
    public String getBankArea() {
        return bankArea;
    }

    /**
     * 区域
     * @param bankArea 区域
     */
    public void setBankArea(String bankArea) {
        this.bankArea = bankArea == null ? null : bankArea.trim();
    }

    /**
     * 操作前余额
     * @return account_before 操作前余额
     */
    public BigDecimal getAccountBefore() {
        return accountBefore;
    }

    /**
     * 操作前余额
     * @param accountBefore 操作前余额
     */
    public void setAccountBefore(BigDecimal accountBefore) {
        this.accountBefore = accountBefore;
    }

    /**
     * 操作后余额
     * @return account_after 操作后余额
     */
    public BigDecimal getAccountAfter() {
        return accountAfter;
    }

    /**
     * 操作后余额
     * @param accountAfter 操作后余额
     */
    public void setAccountAfter(BigDecimal accountAfter) {
        this.accountAfter = accountAfter;
    }

    /**
     * 回撤时间
     * @return withdraw_time 回撤时间
     */
    public Date getWithdrawTime() {
        return withdrawTime;
    }

    /**
     * 回撤时间
     * @param withdrawTime 回撤时间
     */
    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    /**
     * 人民币
     * @return withdraw_rmb 人民币
     */
    public BigDecimal getWithdrawRmb() {
        return withdrawRmb;
    }

    /**
     * 人民币
     * @param withdrawRmb 人民币
     */
    public void setWithdrawRmb(BigDecimal withdrawRmb) {
        this.withdrawRmb = withdrawRmb;
    }

    /**
     * 回撤状态（0 成功，1 失败，2 执行中）
     * @return withdraw_state 回撤状态（0 成功，1 失败，2 执行中）
     */
    public Byte getWithdrawState() {
        return withdrawState;
    }

    /**
     * 回撤状态（0 成功，1 失败，2 执行中）
     * @param withdrawState 回撤状态（0 成功，1 失败，2 执行中）
     */
    public void setWithdrawState(Byte withdrawState) {
        this.withdrawState = withdrawState;
    }

    /**
     * 备注
     * @return comment 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 备注
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}