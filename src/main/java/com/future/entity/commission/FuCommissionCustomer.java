package com.future.entity.commission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuCommissionCustomer {
    public static String SOURCE_USER_ID ="source_user_id";
    public static String SOURCE_ACCOUNT_ID ="source_account_id";
    public static String SOURCE_ORDER_ID ="source_order_id";
    public static String COMMISSION_USER_ID ="commission_user_id";
    public static String COMMISSION_DATE ="commission_date";
    public static String COMMISSION_TYPE ="commission_type";
    public static String COMMISSION_USER_LEVEL ="commission_user_level";
    public static String COMMISSION_ACCOUNT_ID ="commission_account_id";

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
     * 交易时间
     */
    private Date operDate;

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
    private String sourceOrderId;

    /**
     * 原订单状态
     */
    private Integer sourceOrderState;

    /**
     * 原订单操作类型
     */
    private Integer sourceOrderOper;

    /**
     * 源金额
     */
    private BigDecimal sourceOrderMoney;

    /**
     * 订单手数
     */
    private BigDecimal sourceOrderLots;

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    private Integer commissionType;

    /**
     * 收佣用户ID
     */
    private Integer commissionUserId;

    /**
     * 收佣用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer commissionUserType;

    /**
     * 收佣等级（1级、2级、3级）
     */
    private Integer commissionUserLevel;

    /**
     * 收佣账户ID
     */
    private Integer commissionAccountId;

    /**
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    private Integer commissionRateType;

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
    private Integer commissionState;

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
    public String getSourceOrderId() {
        return sourceOrderId;
    }

    /**
     * 源订单号
     * @param sourceOrderId 源订单号
     */
    public void setSourceOrderId(String sourceOrderId) {
        this.sourceOrderId = sourceOrderId == null ? null : sourceOrderId.trim();
    }

    /**
     * 原订单状态
     * @return source_order_state 原订单状态
     */
    public Integer getSourceOrderState() {
        return sourceOrderState;
    }

    /**
     * 原订单状态
     * @param sourceOrderState 原订单状态
     */
    public void setSourceOrderState(Integer sourceOrderState) {
        this.sourceOrderState = sourceOrderState;
    }

    /**
     * 原订单操作类型
     * @return source_order_oper 原订单操作类型
     */
    public Integer getSourceOrderOper() {
        return sourceOrderOper;
    }

    /**
     * 原订单操作类型
     * @param sourceOrderOper 原订单操作类型
     */
    public void setSourceOrderOper(Integer sourceOrderOper) {
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
     * 订单手数
     * @return source_order_lots 订单手数
     */
    public BigDecimal getSourceOrderLots() {
        return sourceOrderLots;
    }

    /**
     * 订单手数
     * @param sourceOrderLots 订单手数
     */
    public void setSourceOrderLots(BigDecimal sourceOrderLots) {
        this.sourceOrderLots = sourceOrderLots;
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
     * 收佣用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     * @return commission_user_type 收佣用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public Integer getCommissionUserType() {
        return commissionUserType;
    }

    /**
     * 收佣用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     * @param commissionUserType 收佣用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public void setCommissionUserType(Integer commissionUserType) {
        this.commissionUserType = commissionUserType;
    }

    /**
     * 收佣等级（1级、2级、3级）
     * @return commission_user_level 收佣等级（1级、2级、3级）
     */
    public Integer getCommissionUserLevel() {
        return commissionUserLevel;
    }

    /**
     * 收佣等级（1级、2级、3级）
     * @param commissionUserLevel 收佣等级（1级、2级、3级）
     */
    public void setCommissionUserLevel(Integer commissionUserLevel) {
        this.commissionUserLevel = commissionUserLevel;
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
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     * @return commission_rate_type 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    public Integer getCommissionRateType() {
        return commissionRateType;
    }

    /**
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     * @param commissionRateType 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    public void setCommissionRateType(Integer commissionRateType) {
        this.commissionRateType = commissionRateType;
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
}