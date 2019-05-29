package com.future.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountCommission {
    /**
     * 
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 佣金余额
     */
    private BigDecimal commissionMoney;

    /**
     * 已提取佣金
     */
    private BigDecimal commissionPaid;

    /**
     * 总佣金额
     */
    private BigDecimal commissionTotal;

    /**
     * 源发生金额
     */
    private BigDecimal commissionSourceMoney;

    /**
     * 返佣等级
     */
    private Byte commissionLevel;

    /**
     * 返佣比率
     */
    private BigDecimal commissionRate;

    /**
     * 期债当前量
     */
    private BigDecimal commissionDebtAmt;

    /**
     * 佣金账户状态（0 正常，1冻结，2 删除）
     */
    private Byte accountState;

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
     * 账户id
     * @return account_id 账户id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 账户id
     * @param accountId 账户id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 佣金余额
     * @return commission_money 佣金余额
     */
    public BigDecimal getCommissionMoney() {
        return commissionMoney;
    }

    /**
     * 佣金余额
     * @param commissionMoney 佣金余额
     */
    public void setCommissionMoney(BigDecimal commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    /**
     * 已提取佣金
     * @return commission_paid 已提取佣金
     */
    public BigDecimal getCommissionPaid() {
        return commissionPaid;
    }

    /**
     * 已提取佣金
     * @param commissionPaid 已提取佣金
     */
    public void setCommissionPaid(BigDecimal commissionPaid) {
        this.commissionPaid = commissionPaid;
    }

    /**
     * 总佣金额
     * @return commission_total 总佣金额
     */
    public BigDecimal getCommissionTotal() {
        return commissionTotal;
    }

    /**
     * 总佣金额
     * @param commissionTotal 总佣金额
     */
    public void setCommissionTotal(BigDecimal commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    /**
     * 源发生金额
     * @return commission_source_money 源发生金额
     */
    public BigDecimal getCommissionSourceMoney() {
        return commissionSourceMoney;
    }

    /**
     * 源发生金额
     * @param commissionSourceMoney 源发生金额
     */
    public void setCommissionSourceMoney(BigDecimal commissionSourceMoney) {
        this.commissionSourceMoney = commissionSourceMoney;
    }

    /**
     * 返佣等级
     * @return commission_level 返佣等级
     */
    public Byte getCommissionLevel() {
        return commissionLevel;
    }

    /**
     * 返佣等级
     * @param commissionLevel 返佣等级
     */
    public void setCommissionLevel(Byte commissionLevel) {
        this.commissionLevel = commissionLevel;
    }

    /**
     * 返佣比率
     * @return commission_rate 返佣比率
     */
    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    /**
     * 返佣比率
     * @param commissionRate 返佣比率
     */
    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    /**
     * 期债当前量
     * @return commission_debt_amt 期债当前量
     */
    public BigDecimal getCommissionDebtAmt() {
        return commissionDebtAmt;
    }

    /**
     * 期债当前量
     * @param commissionDebtAmt 期债当前量
     */
    public void setCommissionDebtAmt(BigDecimal commissionDebtAmt) {
        this.commissionDebtAmt = commissionDebtAmt;
    }

    /**
     * 佣金账户状态（0 正常，1冻结，2 删除）
     * @return account_state 佣金账户状态（0 正常，1冻结，2 删除）
     */
    public Byte getAccountState() {
        return accountState;
    }

    /**
     * 佣金账户状态（0 正常，1冻结，2 删除）
     * @param accountState 佣金账户状态（0 正常，1冻结，2 删除）
     */
    public void setAccountState(Byte accountState) {
        this.accountState = accountState;
    }
}