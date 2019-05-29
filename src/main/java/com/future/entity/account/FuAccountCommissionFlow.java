package com.future.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountCommissionFlow {
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
     * 收佣用户ID
     */
    private Integer commissionUserId;

    /**
     * 收佣账户ID
     */
    private Integer commissionAccountId;

    /**
     * 返佣日期
     */
    private Date commissionDate;

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    private Byte commissionType;

    /**
     * 返佣等级
     */
    private Byte commissionLevel;

    /**
     * 返佣比率
     */
    private Byte commissionRate;

    /**
     * 源金额
     */
    private BigDecimal sourceMoney;

    /**
     * 返佣佣金
     */
    private BigDecimal commissionMoney;

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     */
    private Byte commissionState;

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     */
    private Byte coinType;

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
     * 收佣用户ID
     * @return commission_user_id 收佣用户ID
     */
    public Integer getCommissionUserId() {
        return commissionUserId;
    }

    /**
     * 收佣用户ID
     * @param commissionUserId 收佣用户ID
     */
    public void setCommissionUserId(Integer commissionUserId) {
        this.commissionUserId = commissionUserId;
    }

    /**
     * 收佣账户ID
     * @return commission_account_id 收佣账户ID
     */
    public Integer getCommissionAccountId() {
        return commissionAccountId;
    }

    /**
     * 收佣账户ID
     * @param commissionAccountId 收佣账户ID
     */
    public void setCommissionAccountId(Integer commissionAccountId) {
        this.commissionAccountId = commissionAccountId;
    }

    /**
     * 返佣日期
     * @return commission_date 返佣日期
     */
    public Date getCommissionDate() {
        return commissionDate;
    }

    /**
     * 返佣日期
     * @param commissionDate 返佣日期
     */
    public void setCommissionDate(Date commissionDate) {
        this.commissionDate = commissionDate;
    }

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @return commission_type 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public Byte getCommissionType() {
        return commissionType;
    }

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @param commissionType 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public void setCommissionType(Byte commissionType) {
        this.commissionType = commissionType;
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
    public Byte getCommissionRate() {
        return commissionRate;
    }

    /**
     * 返佣比率
     * @param commissionRate 返佣比率
     */
    public void setCommissionRate(Byte commissionRate) {
        this.commissionRate = commissionRate;
    }

    /**
     * 源金额
     * @return source_money 源金额
     */
    public BigDecimal getSourceMoney() {
        return sourceMoney;
    }

    /**
     * 源金额
     * @param sourceMoney 源金额
     */
    public void setSourceMoney(BigDecimal sourceMoney) {
        this.sourceMoney = sourceMoney;
    }

    /**
     * 返佣佣金
     * @return commission_money 返佣佣金
     */
    public BigDecimal getCommissionMoney() {
        return commissionMoney;
    }

    /**
     * 返佣佣金
     * @param commissionMoney 返佣佣金
     */
    public void setCommissionMoney(BigDecimal commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     * @return commission_state 返佣状态（0 成功，1失败，2 执行中）
     */
    public Byte getCommissionState() {
        return commissionState;
    }

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     * @param commissionState 返佣状态（0 成功，1失败，2 执行中）
     */
    public void setCommissionState(Byte commissionState) {
        this.commissionState = commissionState;
    }

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     * @return coin_type 币种类型（0 余额，1社区币，2 期债）
     */
    public Byte getCoinType() {
        return coinType;
    }

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     * @param coinType 币种类型（0 余额，1社区币，2 期债）
     */
    public void setCoinType(Byte coinType) {
        this.coinType = coinType;
    }
}