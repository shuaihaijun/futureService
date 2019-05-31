package com.future.entity.product;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignal {
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
     * 用户ID
     */
    private Integer userId;

    /**
     * 信号源名称
     */
    private String signalName;

    /**
     * 信号源等级
     */
    private Byte signalLevel;

    /**
     * 信号源描述
     */
    private String signalDesc;

    /**
     * 信号源综合评分
     */
    private Byte signalRatings;

    /**
     * 信号源状态（0 正常，1 隐藏，2 删除）
     */
    private Byte signalState;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 审核时间
     */
    private Date checkDate;

    /**
     * 团队描述
     */
    private String signalTem;

    /**
     * 主要交易币种
     */
    private String signalCurrency;

    /**
     * 月均收益
     */
    private BigDecimal monthlyAverageIncome;

    /**
     * 历史收益
     */
    private BigDecimal historyIncome;

    /**
     * 历史最大回撤
     */
    private BigDecimal historyWithdraw;

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
     * 信号源名称
     * @return signal_name 信号源名称
     */
    public String getSignalName() {
        return signalName;
    }

    /**
     * 信号源名称
     * @param signalName 信号源名称
     */
    public void setSignalName(String signalName) {
        this.signalName = signalName == null ? null : signalName.trim();
    }

    /**
     * 信号源等级
     * @return signal_level 信号源等级
     */
    public Byte getSignalLevel() {
        return signalLevel;
    }

    /**
     * 信号源等级
     * @param signalLevel 信号源等级
     */
    public void setSignalLevel(Byte signalLevel) {
        this.signalLevel = signalLevel;
    }

    /**
     * 信号源描述
     * @return signal_desc 信号源描述
     */
    public String getSignalDesc() {
        return signalDesc;
    }

    /**
     * 信号源描述
     * @param signalDesc 信号源描述
     */
    public void setSignalDesc(String signalDesc) {
        this.signalDesc = signalDesc == null ? null : signalDesc.trim();
    }

    /**
     * 信号源综合评分
     * @return signal_ratings 信号源综合评分
     */
    public Byte getSignalRatings() {
        return signalRatings;
    }

    /**
     * 信号源综合评分
     * @param signalRatings 信号源综合评分
     */
    public void setSignalRatings(Byte signalRatings) {
        this.signalRatings = signalRatings;
    }

    /**
     * 信号源状态（0 正常，1 隐藏，2 删除）
     * @return signal_state 信号源状态（0 正常，1 隐藏，2 删除）
     */
    public Byte getSignalState() {
        return signalState;
    }

    /**
     * 信号源状态（0 正常，1 隐藏，2 删除）
     * @param signalState 信号源状态（0 正常，1 隐藏，2 删除）
     */
    public void setSignalState(Byte signalState) {
        this.signalState = signalState;
    }

    /**
     * 申请时间
     * @return apply_date 申请时间
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * 申请时间
     * @param applyDate 申请时间
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * 审核时间
     * @return check_date 审核时间
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     * 审核时间
     * @param checkDate 审核时间
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * 团队描述
     * @return signal_tem 团队描述
     */
    public String getSignalTem() {
        return signalTem;
    }

    /**
     * 团队描述
     * @param signalTem 团队描述
     */
    public void setSignalTem(String signalTem) {
        this.signalTem = signalTem == null ? null : signalTem.trim();
    }

    /**
     * 主要交易币种
     * @return signal_currency 主要交易币种
     */
    public String getSignalCurrency() {
        return signalCurrency;
    }

    /**
     * 主要交易币种
     * @param signalCurrency 主要交易币种
     */
    public void setSignalCurrency(String signalCurrency) {
        this.signalCurrency = signalCurrency == null ? null : signalCurrency.trim();
    }

    /**
     * 月均收益
     * @return monthly_average_income 月均收益
     */
    public BigDecimal getMonthlyAverageIncome() {
        return monthlyAverageIncome;
    }

    /**
     * 月均收益
     * @param monthlyAverageIncome 月均收益
     */
    public void setMonthlyAverageIncome(BigDecimal monthlyAverageIncome) {
        this.monthlyAverageIncome = monthlyAverageIncome;
    }

    /**
     * 历史收益
     * @return history_income 历史收益
     */
    public BigDecimal getHistoryIncome() {
        return historyIncome;
    }

    /**
     * 历史收益
     * @param historyIncome 历史收益
     */
    public void setHistoryIncome(BigDecimal historyIncome) {
        this.historyIncome = historyIncome;
    }

    /**
     * 历史最大回撤
     * @return history_withdraw 历史最大回撤
     */
    public BigDecimal getHistoryWithdraw() {
        return historyWithdraw;
    }

    /**
     * 历史最大回撤
     * @param historyWithdraw 历史最大回撤
     */
    public void setHistoryWithdraw(BigDecimal historyWithdraw) {
        this.historyWithdraw = historyWithdraw;
    }
}