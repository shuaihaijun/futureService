package com.future.entity.product;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignalApply {

    public static final String APPLY_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String SIGNAL_NAME = "signal_name";
    public static final String APPLY_STATE = "apply_state";
    public static final String SERVER_NAME = "server_name";
    public static final String MT_ACC_ID = "mt_acc_id";
    public static final String CREATE_DATE = "create_date";
    public static final String APPLY_DATE = "apply_date";
    public static final String CHECK_DATE = "check_date";
    /**
     *
     */
    @TableId(type = IdType.AUTO)
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
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * qq号码
     */
    private String qqNumber;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * mt平台账号
     */
    private String mtAccId;

    /**
     * 密码交易
     */
    private String mtPasswordTrade;

    /**
     * 观摩密码
     */
    private String mtPasswordWatch;

    /**
     * 最少参与资金
     */
    private BigDecimal minimum;

    /**
     * 年化预期收益率
     */
    private BigDecimal annualizedExpectedReturn;

    /**
     * 历史收益率(年)
     */
    private BigDecimal historicalReturn;

    /**
     * 建议跟随周期
     */
    private BigDecimal suggestCycle;
    /**
     * 备注
     */
    private String remarks;

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

    /**
     * 电子邮件
     * @return email 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮件
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 电话号码
     * @return phone 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话号码
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * qq号码
     * @return qq_number qq号码
     */
    public String getQqNumber() {
        return qqNumber;
    }

    /**
     * qq号码
     * @param qqNumber qq号码
     */
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber == null ? null : qqNumber.trim();
    }

    /**
     * 服务器名称
     * @return server_name 服务器名称
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * 服务器名称
     * @param serverName 服务器名称
     */
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    /**
     * mt平台账号
     * @return mt_acc_id mt平台账号
     */
    public String getMtAccId() {
        return mtAccId;
    }

    /**
     * mt平台账号
     * @param mtAccId mt平台账号
     */
    public void setMtAccId(String mtAccId) {
        this.mtAccId = mtAccId == null ? null : mtAccId.trim();
    }

    /**
     * 密码交易
     * @return mt_password_trade 密码交易
     */
    public String getMtPasswordTrade() {
        return mtPasswordTrade;
    }

    /**
     * 密码交易
     * @param mtPasswordTrade 密码交易
     */
    public void setMtPasswordTrade(String mtPasswordTrade) {
        this.mtPasswordTrade = mtPasswordTrade == null ? null : mtPasswordTrade.trim();
    }

    /**
     * 观摩密码
     * @return mt_password_watch 观摩密码
     */
    public String getMtPasswordWatch() {
        return mtPasswordWatch;
    }

    /**
     * 观摩密码
     * @param mtPasswordWatch 观摩密码
     */
    public void setMtPasswordWatch(String mtPasswordWatch) {
        this.mtPasswordWatch = mtPasswordWatch == null ? null : mtPasswordWatch.trim();
    }

    /**
     * 最少参与资金
     * @return minimum 最少参与资金
     */
    public BigDecimal getMinimum() {
        return minimum;
    }

    /**
     * 最少参与资金
     * @param minimum 最少参与资金
     */
    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    /**
     * 年化预期收益率
     * @return annualized_expected_return 年化预期收益率
     */
    public BigDecimal getAnnualizedExpectedReturn() {
        return annualizedExpectedReturn;
    }

    /**
     * 年化预期收益率
     * @param annualizedExpectedReturn 年化预期收益率
     */
    public void setAnnualizedExpectedReturn(BigDecimal annualizedExpectedReturn) {
        this.annualizedExpectedReturn = annualizedExpectedReturn;
    }

    /**
     * 历史收益率(年)
     * @return historical_return 历史收益率(年)
     */
    public BigDecimal getHistoricalReturn() {
        return historicalReturn;
    }

    /**
     * 历史收益率(年)
     * @param historicalReturn 历史收益率(年)
     */
    public void setHistoricalReturn(BigDecimal historicalReturn) {
        this.historicalReturn = historicalReturn;
    }

    /**
     * 建议跟随周期
     * @return suggest_cycle 建议跟随周期
     */
    public BigDecimal getSuggestCycle() {
        return suggestCycle;
    }

    /**
     * 建议跟随周期
     * @param suggestCycle 建议跟随周期
     */
    public void setSuggestCycle(BigDecimal suggestCycle) {
        this.suggestCycle = suggestCycle;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}