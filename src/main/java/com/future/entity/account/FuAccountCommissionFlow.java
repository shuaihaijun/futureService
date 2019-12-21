package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountCommissionFlow {

    public static String USER_ID ="user_id";
    public static String ACCOUNT_ID ="account_id";
    public static String COMMISSION_TYPE ="commission_type";
    public static String COMMISSION_USER_TYPE ="commission_user_type";
    public static String COMMISSION_LEVEL ="commission_level";
    public static String COMMISSION_DATE ="commission_date";
    public static String COMMISSION_STATE ="commission_state";
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
    private Integer userId;

    /**
     * 收佣账户ID
     */
    private Integer accountId;

    /**
     * 返佣日期
     */
    private Date commissionDate;

    /**
     * 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    private Integer commissionType;

    /**
     * 收佣用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer commissionUserType;

    /**
     * 收佣等级（1级、2级、3级）
     */
    private Integer commissionLevel;

    /**
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    private Integer commissionRateType;

    /**
     * 返佣比率
     */
    private BigDecimal commissionRate;

    /**
     * 源金额
     */
    private BigDecimal sourceMoney;

    /**
     * 源手数
     */
    private BigDecimal sourceLots;

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
     * @return user_id 收佣用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 收佣用户ID
     * @param userId 收佣用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 收佣账户ID
     * @return account_id 收佣账户ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 收佣账户ID
     * @param accountId 收佣账户ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
     * @return commission_level 收佣等级（1级、2级、3级）
     */
    public Integer getCommissionLevel() {
        return commissionLevel;
    }

    /**
     * 收佣等级（1级、2级、3级）
     * @param commissionLevel 收佣等级（1级、2级、3级）
     */
    public void setCommissionLevel(Integer commissionLevel) {
        this.commissionLevel = commissionLevel;
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
     * 源手数
     * @return source_lots 源手数
     */
    public BigDecimal getSourceLots() {
        return sourceLots;
    }

    /**
     * 源手数
     * @param sourceLots 源手数
     */
    public void setSourceLots(BigDecimal sourceLots) {
        this.sourceLots = sourceLots;
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