package com.future.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountCommunity {
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
     * 社区总收益
     */
    private String comCommodity;

    /**
     * 社区账户余额
     */
    private BigDecimal comMoney;

    /**
     * 社区历史总额
     */
    private BigDecimal comInvestment;

    /**
     * 社区币历史总额(用户充值为负，支付冲正）
     */
    private BigDecimal comCoinInvestment;

    /**
     * 社区币当前量
     */
    private BigDecimal comCoinAmt;

    /**
     * 期债历史总额
     */
    private BigDecimal comDebtInvestment;

    /**
     * 期债当前量
     */
    private BigDecimal comDebtAmt;

    /**
     * 社区银行卡
     */
    private String comBankName;

    /**
     * 社区银行卡号
     */
    private String comBankCode;

    /**
     * 账户上次余额
     */
    private BigDecimal lastAmt;

    /**
     * 上次回撤金额
     */
    private BigDecimal lastWithdrawAmt;

    /**
     * 上次回撤时间
     */
    private Date lastWithdrawTiime;

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
     * 社区总收益
     * @return com_commodity 社区总收益
     */
    public String getComCommodity() {
        return comCommodity;
    }

    /**
     * 社区总收益
     * @param comCommodity 社区总收益
     */
    public void setComCommodity(String comCommodity) {
        this.comCommodity = comCommodity == null ? null : comCommodity.trim();
    }

    /**
     * 社区账户余额
     * @return com_money 社区账户余额
     */
    public BigDecimal getComMoney() {
        return comMoney;
    }

    /**
     * 社区账户余额
     * @param comMoney 社区账户余额
     */
    public void setComMoney(BigDecimal comMoney) {
        this.comMoney = comMoney;
    }

    /**
     * 社区历史总额
     * @return com_investment 社区历史总额
     */
    public BigDecimal getComInvestment() {
        return comInvestment;
    }

    /**
     * 社区历史总额
     * @param comInvestment 社区历史总额
     */
    public void setComInvestment(BigDecimal comInvestment) {
        this.comInvestment = comInvestment;
    }

    /**
     * 社区币历史总额(用户充值为负，支付冲正）
     * @return com_coin_investment 社区币历史总额(用户充值为负，支付冲正）
     */
    public BigDecimal getComCoinInvestment() {
        return comCoinInvestment;
    }

    /**
     * 社区币历史总额(用户充值为负，支付冲正）
     * @param comCoinInvestment 社区币历史总额(用户充值为负，支付冲正）
     */
    public void setComCoinInvestment(BigDecimal comCoinInvestment) {
        this.comCoinInvestment = comCoinInvestment;
    }

    /**
     * 社区币当前量
     * @return com_coin_amt 社区币当前量
     */
    public BigDecimal getComCoinAmt() {
        return comCoinAmt;
    }

    /**
     * 社区币当前量
     * @param comCoinAmt 社区币当前量
     */
    public void setComCoinAmt(BigDecimal comCoinAmt) {
        this.comCoinAmt = comCoinAmt;
    }

    /**
     * 期债历史总额
     * @return com_debt_investment 期债历史总额
     */
    public BigDecimal getComDebtInvestment() {
        return comDebtInvestment;
    }

    /**
     * 期债历史总额
     * @param comDebtInvestment 期债历史总额
     */
    public void setComDebtInvestment(BigDecimal comDebtInvestment) {
        this.comDebtInvestment = comDebtInvestment;
    }

    /**
     * 期债当前量
     * @return com_debt_amt 期债当前量
     */
    public BigDecimal getComDebtAmt() {
        return comDebtAmt;
    }

    /**
     * 期债当前量
     * @param comDebtAmt 期债当前量
     */
    public void setComDebtAmt(BigDecimal comDebtAmt) {
        this.comDebtAmt = comDebtAmt;
    }

    /**
     * 社区银行卡
     * @return com_bank_name 社区银行卡
     */
    public String getComBankName() {
        return comBankName;
    }

    /**
     * 社区银行卡
     * @param comBankName 社区银行卡
     */
    public void setComBankName(String comBankName) {
        this.comBankName = comBankName == null ? null : comBankName.trim();
    }

    /**
     * 社区银行卡号
     * @return com_bank_code 社区银行卡号
     */
    public String getComBankCode() {
        return comBankCode;
    }

    /**
     * 社区银行卡号
     * @param comBankCode 社区银行卡号
     */
    public void setComBankCode(String comBankCode) {
        this.comBankCode = comBankCode == null ? null : comBankCode.trim();
    }

    /**
     * 账户上次余额
     * @return last_amt 账户上次余额
     */
    public BigDecimal getLastAmt() {
        return lastAmt;
    }

    /**
     * 账户上次余额
     * @param lastAmt 账户上次余额
     */
    public void setLastAmt(BigDecimal lastAmt) {
        this.lastAmt = lastAmt;
    }

    /**
     * 上次回撤金额
     * @return last_withdraw_amt 上次回撤金额
     */
    public BigDecimal getLastWithdrawAmt() {
        return lastWithdrawAmt;
    }

    /**
     * 上次回撤金额
     * @param lastWithdrawAmt 上次回撤金额
     */
    public void setLastWithdrawAmt(BigDecimal lastWithdrawAmt) {
        this.lastWithdrawAmt = lastWithdrawAmt;
    }

    /**
     * 上次回撤时间
     * @return last_withdraw_tiime 上次回撤时间
     */
    public Date getLastWithdrawTiime() {
        return lastWithdrawTiime;
    }

    /**
     * 上次回撤时间
     * @param lastWithdrawTiime 上次回撤时间
     */
    public void setLastWithdrawTiime(Date lastWithdrawTiime) {
        this.lastWithdrawTiime = lastWithdrawTiime;
    }
}