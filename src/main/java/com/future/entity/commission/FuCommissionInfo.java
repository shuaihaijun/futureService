package com.future.entity.commission;

import java.math.BigDecimal;
import java.util.Date;

public class FuCommissionInfo {
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
     * 交易时间
     */
    private Date operDate;

    /**
     * 佣金来源（0 社区跟单，1 平台返佣，2 信号源租金）
     */
    private int sourceType;

    /**
     * 源用户ID
     */
    private Integer sourceUserId;

    /**
     * 源账户id
     */
    private Integer sourceAccountId;

    /**
     * 源订单号
     */
    private Integer sourceOrderId;

    /**
     * 原订单状态
     */
    private int sourceOrderState;

    /**
     * 源订单操作类型
     */
    private int sourceOrderOper;

    /**
     * 源金额
     */
    private BigDecimal sourceOrderMoney;

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    private int commissionType;

    /**
     * 收佣用户ID
     */
    private Integer commissionUserId;

    /**
     * 收佣用户类型
     */
    private int commissionUserType;

    /**
     * 收佣用户等级
     */
    private int commissionLuserEvel;

    /**
     * 收佣账户ID
     */
    private Integer commissionAccountId;

    /**
     * 佣金比例
     */
    private BigDecimal commissionRate;

    /**
     * 返佣佣金
     */
    private BigDecimal commissionMoney;

    /**
     * 发生佣金时间
     */
    private Date commissionDate;

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     */
    private int commissionState;

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     */
    private int coinType;

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
     * 交易时间
     * @return oper_date 交易时间
     */
    public Date getOperDate() {
        return operDate;
    }

    /**
     * 交易时间
     * @param operDate 交易时间
     */
    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    /**
     * 佣金来源（0 社区跟单，1 平台返佣，2 信号源租金）
     * @return source_type 佣金来源（0 社区跟单，1 平台返佣，2 信号源租金）
     */
    public int getSourceType() {
        return sourceType;
    }

    /**
     * 佣金来源（0 社区跟单，1 平台返佣，2 信号源租金）
     * @param sourceType 佣金来源（0 社区跟单，1 平台返佣，2 信号源租金）
     */
    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 源用户ID
     * @return source_user_id 源用户ID
     */
    public Integer getSourceUserId() {
        return sourceUserId;
    }

    /**
     * 源用户ID
     * @param sourceUserId 源用户ID
     */
    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    /**
     * 源账户id
     * @return source_account_id 源账户id
     */
    public Integer getSourceAccountId() {
        return sourceAccountId;
    }

    /**
     * 源账户id
     * @param sourceAccountId 源账户id
     */
    public void setSourceAccountId(Integer sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    /**
     * 源订单号
     * @return source_order_id 源订单号
     */
    public Integer getSourceOrderId() {
        return sourceOrderId;
    }

    /**
     * 源订单号
     * @param sourceOrderId 源订单号
     */
    public void setSourceOrderId(Integer sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    /**
     * 原订单状态
     * @return source_order_state 原订单状态
     */
    public int getSourceOrderState() {
        return sourceOrderState;
    }

    /**
     * 原订单状态
     * @param sourceOrderState 原订单状态
     */
    public void setSourceOrderState(int sourceOrderState) {
        this.sourceOrderState = sourceOrderState;
    }

    /**
     * 源订单操作类型
     * @return source_order_oper 源订单操作类型
     */
    public int getSourceOrderOper() {
        return sourceOrderOper;
    }

    /**
     * 源订单操作类型
     * @param sourceOrderOper 源订单操作类型
     */
    public void setSourceOrderOper(int sourceOrderOper) {
        this.sourceOrderOper = sourceOrderOper;
    }

    /**
     * 源金额
     * @return source_order_money 源金额
     */
    public BigDecimal getSourceOrderMoney() {
        return sourceOrderMoney;
    }

    /**
     * 源金额
     * @param sourceOrderMoney 源金额
     */
    public void setSourceOrderMoney(BigDecimal sourceOrderMoney) {
        this.sourceOrderMoney = sourceOrderMoney;
    }

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @return commission_type 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public int getCommissionType() {
        return commissionType;
    }

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @param commissionType 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
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
     * 收佣用户类型
     * @return commission_user_type 收佣用户类型
     */
    public int getCommissionUserType() {
        return commissionUserType;
    }

    /**
     * 收佣用户类型
     * @param commissionUserType 收佣用户类型
     */
    public void setCommissionUserType(int commissionUserType) {
        this.commissionUserType = commissionUserType;
    }

    /**
     * 收佣用户等级
     * @return commission_luser_evel 收佣用户等级
     */
    public int getCommissionLuserEvel() {
        return commissionLuserEvel;
    }

    /**
     * 收佣用户等级
     * @param commissionLuserEvel 收佣用户等级
     */
    public void setCommissionLuserEvel(int commissionLuserEvel) {
        this.commissionLuserEvel = commissionLuserEvel;
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
     * 佣金比例
     * @return commission_rate 佣金比例
     */
    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    /**
     * 佣金比例
     * @param commissionRate 佣金比例
     */
    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
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
     * 发生佣金时间
     * @return commission_date 发生佣金时间
     */
    public Date getCommissionDate() {
        return commissionDate;
    }

    /**
     * 发生佣金时间
     * @param commissionDate 发生佣金时间
     */
    public void setCommissionDate(Date commissionDate) {
        this.commissionDate = commissionDate;
    }

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     * @return commission_state 返佣状态（0 成功，1失败，2 执行中）
     */
    public int getCommissionState() {
        return commissionState;
    }

    /**
     * 返佣状态（0 成功，1失败，2 执行中）
     * @param commissionState 返佣状态（0 成功，1失败，2 执行中）
     */
    public void setCommissionState(int commissionState) {
        this.commissionState = commissionState;
    }

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     * @return coin_type 币种类型（0 余额，1社区币，2 期债）
     */
    public int getCoinType() {
        return coinType;
    }

    /**
     * 币种类型（0 余额，1社区币，2 期债）
     * @param coinType 币种类型（0 余额，1社区币，2 期债）
     */
    public void setCoinType(int coinType) {
        this.coinType = coinType;
    }
}