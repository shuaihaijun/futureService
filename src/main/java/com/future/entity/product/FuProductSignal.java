package com.future.entity.product;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignal {
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
     * 交易手数
     */
    private Integer signalLots;

    /**
     * 交易赢手数
     */
    private Integer signalLotsProfit;

    /**
     * 账户余额
     */
    private BigDecimal signalAccountMoney;

    /**
     * 主要交易币种
     */
    private String signalCurrency;

    /**
     * 跟随人数
     */
    private Integer signalFollows;

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
     * 交易手数
     * @return signal_lots 交易手数
     */
    public Integer getSignalLots() {
        return signalLots;
    }

    /**
     * 交易手数
     * @param signalLots 交易手数
     */
    public void setSignalLots(Integer signalLots) {
        this.signalLots = signalLots;
    }

    /**
     * 交易赢手数
     * @return signal_lots_profit 交易赢手数
     */
    public Integer getSignalLotsProfit() {
        return signalLotsProfit;
    }

    /**
     * 交易赢手数
     * @param signalLotsProfit 交易赢手数
     */
    public void setSignalLotsProfit(Integer signalLotsProfit) {
        this.signalLotsProfit = signalLotsProfit;
    }

    /**
     * 账户余额
     * @return signal_account_money 账户余额
     */
    public BigDecimal getSignalAccountMoney() {
        return signalAccountMoney;
    }

    /**
     * 账户余额
     * @param signalAccountMoney 账户余额
     */
    public void setSignalAccountMoney(BigDecimal signalAccountMoney) {
        this.signalAccountMoney = signalAccountMoney;
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
     * 跟随人数
     * @return signal_follows 跟随人数
     */
    public Integer getSignalFollows() {
        return signalFollows;
    }

    /**
     * 跟随人数
     * @param signalFollows 跟随人数
     */
    public void setSignalFollows(Integer signalFollows) {
        this.signalFollows = signalFollows;
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

    /**
     * 备注
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}