package com.future.entity.product;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductFollow {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 信号源ID
     */
    private Integer productId;

    /**
     * 最大跟单比例（100  1:100,400 1：400）
     */
    private Integer followMaxRate;

    /**
     * 预警金额
     */
    private BigDecimal followAlarmAmount;

    /**
     * 预警线 百分比（ 0.2 , 0.3）
     */
    private BigDecimal followAlarmLevel;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

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
     * 用户ID
     * @return user_id 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 信号源ID
     * @return product_id 信号源ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 信号源ID
     * @param productId 信号源ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 最大跟单比例（100  1:100,400 1：400）
     * @return follow_max_rate 最大跟单比例（100  1:100,400 1：400）
     */
    public Integer getFollowMaxRate() {
        return followMaxRate;
    }

    /**
     * 最大跟单比例（100  1:100,400 1：400）
     * @param followMaxRate 最大跟单比例（100  1:100,400 1：400）
     */
    public void setFollowMaxRate(Integer followMaxRate) {
        this.followMaxRate = followMaxRate;
    }

    /**
     * 预警金额
     * @return follow_alarm_amount 预警金额
     */
    public BigDecimal getFollowAlarmAmount() {
        return followAlarmAmount;
    }

    /**
     * 预警金额
     * @param followAlarmAmount 预警金额
     */
    public void setFollowAlarmAmount(BigDecimal followAlarmAmount) {
        this.followAlarmAmount = followAlarmAmount;
    }

    /**
     * 预警线 百分比（ 0.2 , 0.3）
     * @return follow_alarm_level 预警线 百分比（ 0.2 , 0.3）
     */
    public BigDecimal getFollowAlarmLevel() {
        return followAlarmLevel;
    }

    /**
     * 预警线 百分比（ 0.2 , 0.3）
     * @param followAlarmLevel 预警线 百分比（ 0.2 , 0.3）
     */
    public void setFollowAlarmLevel(BigDecimal followAlarmLevel) {
        this.followAlarmLevel = followAlarmLevel;
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
}