package com.future.entity.product;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignalApply {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 信号源名称
     */
    private String signalName;

    /**
     * 团队描述
     */
    private String signalTem;

    /**
     * 信号源描述
     */
    private String signalDesc;

    /**
     * 主要交易币种
     */
    private String signalCurrency;

    /**
     * 月均收益
     */
    private BigDecimal monthlyAverageIncome;

    /**
     * 历史最大回撤
     */
    private BigDecimal historyWithdraw;

    /**
     * 申请状态（0 暂存，1 待审核，2 通过，3 未通过）
     */
    private int applyState;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 审核时间
     */
    private Date checkDate;

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

    /**
     * 申请状态（0 暂存，1 待审核，2 通过，3 未通过）
     * @return apply_state 申请状态（0 暂存，1 待审核，2 通过，3 未通过）
     */
    public int getApplyState() {
        return applyState;
    }

    /**
     * 申请状态（0 暂存，1 待审核，2 通过，3 未通过）
     * @param applyState 申请状态（0 暂存，1 待审核，2 通过，3 未通过）
     */
    public void setApplyState(int applyState) {
        this.applyState = applyState;
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