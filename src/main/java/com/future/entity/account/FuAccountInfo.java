package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountInfo {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
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
     * 账户等级
     */
    private String accountLevel;

    /**
     * 交易密码
     */
    private String accountSecurities;

    /**
     * 交易的特权
     */
    private String accountOptions;

    /**
     * 社区总收益
     */
    private String accountCommodity;

    /**
     * 社区账户余额
     */
    private BigDecimal accountMoney;

    /**
     * 社区币余额
     */
    private BigDecimal accountCoin;

    /**
     * 期债(需付金额）
     */
    private BigDecimal accountDebt;

    /**
     * 账户上次余额
     */
    private BigDecimal accountLastAmt;

    /**
     * 社区总投资额
     */
    private BigDecimal accountInvestment;

    /**
     * 账户状态（0 正常，1冻结，2 删除）
     */
    private int accountState;

    /**
     * 相关账户信息
     */
    private String isrelativeContent;

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
     * 账户等级
     * @return account_level 账户等级
     */
    public String getAccountLevel() {
        return accountLevel;
    }

    /**
     * 账户等级
     * @param accountLevel 账户等级
     */
    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel == null ? null : accountLevel.trim();
    }

    /**
     * 交易密码
     * @return account_securities 交易密码
     */
    public String getAccountSecurities() {
        return accountSecurities;
    }

    /**
     * 交易密码
     * @param accountSecurities 交易密码
     */
    public void setAccountSecurities(String accountSecurities) {
        this.accountSecurities = accountSecurities == null ? null : accountSecurities.trim();
    }

    /**
     * 交易的特权
     * @return account_options 交易的特权
     */
    public String getAccountOptions() {
        return accountOptions;
    }

    /**
     * 交易的特权
     * @param accountOptions 交易的特权
     */
    public void setAccountOptions(String accountOptions) {
        this.accountOptions = accountOptions == null ? null : accountOptions.trim();
    }

    /**
     * 社区总收益
     * @return account_commodity 社区总收益
     */
    public String getAccountCommodity() {
        return accountCommodity;
    }

    /**
     * 社区总收益
     * @param accountCommodity 社区总收益
     */
    public void setAccountCommodity(String accountCommodity) {
        this.accountCommodity = accountCommodity == null ? null : accountCommodity.trim();
    }

    /**
     * 社区账户余额
     * @return account_money 社区账户余额
     */
    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    /**
     * 社区账户余额
     * @param accountMoney 社区账户余额
     */
    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    /**
     * 社区币余额
     * @return account_coin 社区币余额
     */
    public BigDecimal getAccountCoin() {
        return accountCoin;
    }

    /**
     * 社区币余额
     * @param accountCoin 社区币余额
     */
    public void setAccountCoin(BigDecimal accountCoin) {
        this.accountCoin = accountCoin;
    }

    /**
     * 期债(需付金额）
     * @return account_debt 期债(需付金额）
     */
    public BigDecimal getAccountDebt() {
        return accountDebt;
    }

    /**
     * 期债(需付金额）
     * @param accountDebt 期债(需付金额）
     */
    public void setAccountDebt(BigDecimal accountDebt) {
        this.accountDebt = accountDebt;
    }

    /**
     * 账户上次余额
     * @return account_last_amt 账户上次余额
     */
    public BigDecimal getAccountLastAmt() {
        return accountLastAmt;
    }

    /**
     * 账户上次余额
     * @param accountLastAmt 账户上次余额
     */
    public void setAccountLastAmt(BigDecimal accountLastAmt) {
        this.accountLastAmt = accountLastAmt;
    }

    /**
     * 社区总投资额
     * @return account_investment 社区总投资额
     */
    public BigDecimal getAccountInvestment() {
        return accountInvestment;
    }

    /**
     * 社区总投资额
     * @param accountInvestment 社区总投资额
     */
    public void setAccountInvestment(BigDecimal accountInvestment) {
        this.accountInvestment = accountInvestment;
    }

    /**
     * 账户状态（0 正常，1冻结，2 删除）
     * @return account_state 账户状态（0 正常，1冻结，2 删除）
     */
    public int getAccountState() {
        return accountState;
    }

    /**
     * 账户状态（0 正常，1冻结，2 删除）
     * @param accountState 账户状态（0 正常，1冻结，2 删除）
     */
    public void setAccountState(int accountState) {
        this.accountState = accountState;
    }

    /**
     * 相关账户信息
     * @return isrelative_content 相关账户信息
     */
    public String getIsrelativeContent() {
        return isrelativeContent;
    }

    /**
     * 相关账户信息
     * @param isrelativeContent 相关账户信息
     */
    public void setIsrelativeContent(String isrelativeContent) {
        this.isrelativeContent = isrelativeContent == null ? null : isrelativeContent.trim();
    }
}