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
    private int commissionType;

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     */
    private int orderType;

    /**
     * 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     */
    private int commissionUserType;

    /**
     * 返佣角色等级
     */
    private int commissionUserLevel;

    /**
     * 比率计算类型（0 按原金额，1 按返佣金额）
     */
    private int rateType;

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
    public int getCommissionType() {
        return commissionType;
    }

    /**
     * 佣金发生来源 ( 0 订单返佣，1 其他返佣)
     * @param commissionType 佣金发生来源 ( 0 订单返佣，1 其他返佣)
     */
    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     * @return order_type 订单类型（0 社区跟单，1 自主交易）
     */
    public int getOrderType() {
        return orderType;
    }

    /**
     * 订单类型（0 社区跟单，1 自主交易）
     * @param orderType 订单类型（0 社区跟单，1 自主交易）
     */
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    /**
     * 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     * @return commission_user_type 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     */
    public int getCommissionUserType() {
        return commissionUserType;
    }

    /**
     * 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     * @param commissionUserType 返佣用户类型（0 社区，1 信号源，2 多级返佣，3 IB）
     */
    public void setCommissionUserType(int commissionUserType) {
        this.commissionUserType = commissionUserType;
    }

    /**
     * 返佣角色等级
     * @return commission_user_level 返佣角色等级
     */
    public int getCommissionUserLevel() {
        return commissionUserLevel;
    }

    /**
     * 返佣角色等级
     * @param commissionUserLevel 返佣角色等级
     */
    public void setCommissionUserLevel(int commissionUserLevel) {
        this.commissionUserLevel = commissionUserLevel;
    }

    /**
     * 比率计算类型（0 按原金额，1 按返佣金额）
     * @return rate_type 比率计算类型（0 按原金额，1 按返佣金额）
     */
    public int getRateType() {
        return rateType;
    }

    /**
     * 比率计算类型（0 按原金额，1 按返佣金额）
     * @param rateType 比率计算类型（0 按原金额，1 按返佣金额）
     */
    public void setRateType(int rateType) {
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