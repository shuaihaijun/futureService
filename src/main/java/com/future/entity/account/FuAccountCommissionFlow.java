package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountCommissionFlow {
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
    private Integer commissionType;

    /**
     * 返佣等级
     */
    private Integer commissionLevel;

    /**
     * 返佣比率
     */
    private Integer commissionRate;

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
    private Integer commissionState;

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     */
    private Integer coinType;

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
    public Integer getCommissionType() {
        return commissionType;
    }

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @param commissionType 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public void setCommissionType(Integer commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * 返佣等级
     * @return commission_level 返佣等级
     */
    public Integer getCommissionLevel() {
        return commissionLevel;
    }

    /**
     * 返佣等级
     * @param commissionLevel 返佣等级
     */
    public void setCommissionLevel(Integer commissionLevel) {
        this.commissionLevel = commissionLevel;
    }

    /**
     * 返佣比率
     * @return commission_rate 返佣比率
     */
    public Integer getCommissionRate() {
        return commissionRate;
    }

    /**
     * 返佣比率
     * @param commissionRate 返佣比率
     */
    public void setCommissionRate(Integer commissionRate) {
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
    public Integer getCommissionState() {
        return commissionState;
    }

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     * @param commissionState 返佣状态（0 成功，1失败，2 执行中）
     */
    public void setCommissionState(Integer commissionState) {
        this.commissionState = commissionState;
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
}