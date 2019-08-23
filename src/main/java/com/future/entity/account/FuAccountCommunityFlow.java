package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountCommunityFlow {
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
     * 创建时间
     */
    private Date dealDate;

    /**
     * 结算类型（0 交易返佣，1 用户充值 ，2 平台返佣 ）
     */
    private Integer dealType;

    /**
     * 发生源金额
     */
    private BigDecimal moneySoure;

    /**
     * 排除金额（返佣等金额）
     */
    private BigDecimal moneyExclude;

    /**
     * 需支付金
     */
    private BigDecimal moneyRequired;

    /**
     * 实际交易额（rmb、coin debt）
     */
    private BigDecimal moneyReal;

    /**
     * 前账户余额
     */
    private BigDecimal accountBefore;

    /**
     * 当前账户余额
     */
    private BigDecimal accountAfterAmt;

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     */
    private Integer coinType;

    /**
     * 状态（0 成功，1 失败，2 执行中）
     */
    private Integer state;

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
     * 创建时间
     * @return deal_date 创建时间
     */
    public Date getDealDate() {
        return dealDate;
    }

    /**
     * 创建时间
     * @param dealDate 创建时间
     */
    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    /**
     * 结算类型（0 交易返佣，1 用户充值 ，2 平台返佣 ）
     * @return deal_type 结算类型（0 交易返佣，1 用户充值 ，2 平台返佣 ）
     */
    public Integer getDealType() {
        return dealType;
    }

    /**
     * 结算类型（0 交易返佣，1 用户充值 ，2 平台返佣 ）
     * @param dealType 结算类型（0 交易返佣，1 用户充值 ，2 平台返佣 ）
     */
    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    /**
     * 发生源金额
     * @return money_soure 发生源金额
     */
    public BigDecimal getMoneySoure() {
        return moneySoure;
    }

    /**
     * 发生源金额
     * @param moneySoure 发生源金额
     */
    public void setMoneySoure(BigDecimal moneySoure) {
        this.moneySoure = moneySoure;
    }

    /**
     * 排除金额（返佣等金额）
     * @return money_exclude 排除金额（返佣等金额）
     */
    public BigDecimal getMoneyExclude() {
        return moneyExclude;
    }

    /**
     * 排除金额（返佣等金额）
     * @param moneyExclude 排除金额（返佣等金额）
     */
    public void setMoneyExclude(BigDecimal moneyExclude) {
        this.moneyExclude = moneyExclude;
    }

    /**
     * 需支付金
     * @return money_required 需支付金
     */
    public BigDecimal getMoneyRequired() {
        return moneyRequired;
    }

    /**
     * 需支付金
     * @param moneyRequired 需支付金
     */
    public void setMoneyRequired(BigDecimal moneyRequired) {
        this.moneyRequired = moneyRequired;
    }

    /**
     * 实际交易额（rmb、coin debt）
     * @return money_real 实际交易额（rmb、coin debt）
     */
    public BigDecimal getMoneyReal() {
        return moneyReal;
    }

    /**
     * 实际交易额（rmb、coin debt）
     * @param moneyReal 实际交易额（rmb、coin debt）
     */
    public void setMoneyReal(BigDecimal moneyReal) {
        this.moneyReal = moneyReal;
    }

    /**
     * 前账户余额
     * @return account_before 前账户余额
     */
    public BigDecimal getAccountBefore() {
        return accountBefore;
    }

    /**
     * 前账户余额
     * @param accountBefore 前账户余额
     */
    public void setAccountBefore(BigDecimal accountBefore) {
        this.accountBefore = accountBefore;
    }

    /**
     * 当前账户余额
     * @return account_after_amt 当前账户余额
     */
    public BigDecimal getAccountAfterAmt() {
        return accountAfterAmt;
    }

    /**
     * 当前账户余额
     * @param accountAfterAmt 当前账户余额
     */
    public void setAccountAfterAmt(BigDecimal accountAfterAmt) {
        this.accountAfterAmt = accountAfterAmt;
    }

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     * @return coin_type 币种类型（0 余额，1社区币，2 期债）
     */
    public Integer getCoinType() {
        return coinType;
    }

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     * @param coinType 币种类型（0 余额，1社区币，2 期债）
     */
    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    /**
     * 状态（0 成功，1 失败，2 执行中）
     * @return state 状态（0 成功，1 失败，2 执行中）
     */
    public Integer getState() {
        return state;
    }

    /**
     * 状态（0 成功，1 失败，2 执行中）
     * @param state 状态（0 成功，1 失败，2 执行中）
     */
    public void setState(Integer state) {
        this.state = state;
    }
}