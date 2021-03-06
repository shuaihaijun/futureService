package com.future.entity.commission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuCommissionLevel {

    public static String COMMISSION_TYPE="commission_type";
    public static String ORDER_TYPE="order_type";
    public static String COMMISSION_USER_TYPE="commission_user_type";
    public static String COMMISSION_USER_LEVEL="commission_user_level";
    public static String RATE_TYPE="rate_type";
    public static String LEVEL_NAME="level_name";

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
     * 规则名称（例：信号源返佣规则1-5）
     */
    private String levelName;

    /**
     * 佣金发生来源 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    private Integer commissionType;

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     */
    private Integer orderType;

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer commissionUserType;

    /**
     * 返佣用户等级（1级、2级、3级）
     */
    private Integer commissionUserLevel;

    /**
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    private Integer rateType;

    /**
     * 返佣比率
     */
    private BigDecimal rate;

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
     * 规则名称（例：信号源返佣规则1-5）
     * @return level_name 规则名称（例：信号源返佣规则1-5）
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * 规则名称（例：信号源返佣规则1-5）
     * @param levelName 规则名称（例：信号源返佣规则1-5）
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * 佣金发生来源 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @return commission_type 佣金发生来源 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public Integer getCommissionType() {
        return commissionType;
    }

    /**
     * 佣金发生来源 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     * @param commissionType 佣金发生来源 佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
     */
    public void setCommissionType(Integer commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     * @return order_type 订单类型（0 社区跟单，1 自主交易）
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     * @param orderType 订单类型（0 社区跟单，1 自主交易）
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     * @return commission_user_type 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public Integer getCommissionUserType() {
        return commissionUserType;
    }

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     * @param commissionUserType 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    public void setCommissionUserType(Integer commissionUserType) {
        this.commissionUserType = commissionUserType;
    }

    /**
     * 返佣用户等级（1级、2级、3级）
     * @return commission_user_level 返佣用户等级（1级、2级、3级）
     */
    public Integer getCommissionUserLevel() {
        return commissionUserLevel;
    }

    /**
     * 返佣用户等级（1级、2级、3级）
     * @param commissionUserLevel 返佣用户等级（1级、2级、3级）
     */
    public void setCommissionUserLevel(Integer commissionUserLevel) {
        this.commissionUserLevel = commissionUserLevel;
    }

    /**
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     * @return rate_type 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    public Integer getRateType() {
        return rateType;
    }

    /**
     * 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     * @param rateType 比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）
     */
    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }

    /**
     * 返佣比率
     * @return rate 返佣比率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 返佣比率
     * @param rate 返佣比率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}