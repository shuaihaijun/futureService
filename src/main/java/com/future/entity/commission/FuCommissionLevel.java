package com.future.entity.commission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuCommissionLevel {
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
     * 佣金发生来源 ( 0 订单返佣，1 其他返佣)
     */
    private Integer commissionType;

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     */
    private Integer orderType;

    /**
     * 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     */
    private Integer commissionUserType;

    /**
     * 返佣角色等级
     */
    private Integer commissionUserLevel;

    /**
     * 比率计算类型（0 按原金额，1 按返佣金额）
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
     * 佣金发生来源 ( 0 订单返佣，1 其他返佣)
     * @return commission_type 佣金发生来源 ( 0 订单返佣，1 其他返佣)
     */
    public Integer getCommissionType() {
        return commissionType;
    }

    /**
     * 佣金发生来源 ( 0 订单返佣，1 其他返佣)
     * @param commissionType 佣金发生来源 ( 0 订单返佣，1 其他返佣)
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
     * 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     * @return commission_user_type 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     */
    public Integer getCommissionUserType() {
        return commissionUserType;
    }

    /**
     * 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     * @param commissionUserType 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     */
    public void setCommissionUserType(Integer commissionUserType) {
        this.commissionUserType = commissionUserType;
    }

    /**
     * 返佣角色等级
     * @return commission_user_level 返佣角色等级
     */
    public Integer getCommissionUserLevel() {
        return commissionUserLevel;
    }

    /**
     * 返佣角色等级
     * @param commissionUserLevel 返佣角色等级
     */
    public void setCommissionUserLevel(Integer commissionUserLevel) {
        this.commissionUserLevel = commissionUserLevel;
    }

    /**
     * 比率计算类型（0 按原金额，1 按返佣金额）
     * @return rate_type 比率计算类型（0 按原金额，1 按返佣金额）
     */
    public Integer getRateType() {
        return rateType;
    }

    /**
     * 比率计算类型（0 按原金额，1 按返佣金额）
     * @param rateType 比率计算类型（0 按原金额，1 按返佣金额）
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