package com.future.entity.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuUserFollows {
    public static String USER_ID="user_id";
    public static String USER_MT_ACC_ID="user_mt_acc_id";
    public static String SIGNAL_ID="signal_id";
    public static String SIGNAL_MT_ACC_ID="signal_mt_acc_id";
    public static String RULE_STATE="rule_state";
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
     * 信号源ID
     */
    private Integer signalId;

    /**
     * 用户服务器
     */
    private String userServerName;

    /**
     * 用户mt平台账号
     */
    private String userMtAccId;

    /**
     * 信号源服务器
     */
    private String signalServerName;

    /**
     * 信号源mt平台账号
     */
    private String signalMtAccId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     */
    private Integer ruleType;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 规则状态（0 正常，1  隐藏，2 废弃）
     */
    private Integer ruleState;

    /**
     * 单笔上限金额
     */
    private BigDecimal limitUpperAmount;

    /**
     * 账户最低安全净值
     */
    private BigDecimal accountEquityAmount;

    /**
     * 账户最低安全净值百分比
     */
    private BigDecimal accountEquityPercentage;

    /**
     * 最大跟單金額
     */
    private BigDecimal followMaxAmount;

    /**
     * 预警金额
     */
    private BigDecimal followAlarmAmount;

    /**
     * 预警损失百分比 （ 0.2 , 0.3）
     */
    private BigDecimal followAlarmLose;

    /**
     * 预警线 百分比（ 0.2 , 0.3）
     */
    private BigDecimal followAlarmLevel;


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
     * @return signal_id 信号源ID
     */
    public Integer getSignalId() {
        return signalId;
    }

    /**
     * 信号源ID
     * @param signalId 信号源ID
     */
    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    /**
     * 用户服务器
     * @return user_server_name 用户服务器
     */
    public String getUserServerName() {
        return userServerName;
    }

    /**
     * 用户服务器
     * @param userServerName 用户服务器
     */
    public void setUserServerName(String userServerName) {
        this.userServerName = userServerName == null ? null : userServerName.trim();
    }

    /**
     * 用户mt平台账号
     * @return user_mt_acc_id 用户mt平台账号
     */
    public String getUserMtAccId() {
        return userMtAccId;
    }

    /**
     * 用户mt平台账号
     * @param userMtAccId 用户mt平台账号
     */
    public void setUserMtAccId(String userMtAccId) {
        this.userMtAccId = userMtAccId == null ? null : userMtAccId.trim();
    }

    /**
     * 信号源服务器
     * @return signal_server_name 信号源服务器
     */
    public String getSignalServerName() {
        return signalServerName;
    }

    /**
     * 信号源服务器
     * @param signalServerName 信号源服务器
     */
    public void setSignalServerName(String signalServerName) {
        this.signalServerName = signalServerName == null ? null : signalServerName.trim();
    }

    /**
     * 信号源mt平台账号
     * @return signal_mt_acc_id 信号源mt平台账号
     */
    public String getSignalMtAccId() {
        return signalMtAccId;
    }

    /**
     * 信号源mt平台账号
     * @param signalMtAccId 信号源mt平台账号
     */
    public void setSignalMtAccId(String signalMtAccId) {
        this.signalMtAccId = signalMtAccId == null ? null : signalMtAccId.trim();
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
     * 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     * @return rule_type 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     */
    public Integer getRuleType() {
        return ruleType;
    }

    /**
     * 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     * @param ruleType 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     */
    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * 数量
     * @return amount 数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 数量
     * @param amount 数量
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 规则状态（0 正常，1  隐藏，2 废弃）
     * @return rule_state 规则状态（0 正常，1  隐藏，2 废弃）
     */
    public Integer getRuleState() {
        return ruleState;
    }

    /**
     * 规则状态（0 正常，1  隐藏，2 废弃）
     * @param ruleState 规则状态（0 正常，1  隐藏，2 废弃）
     */
    public void setRuleState(Integer ruleState) {
        this.ruleState = ruleState;
    }

    /**
     * 单笔上限金额
     * @return limit_upper_amount 单笔上限金额
     */
    public BigDecimal getLimitUpperAmount() {
        return limitUpperAmount;
    }

    /**
     * 单笔上限金额
     * @param limitUpperAmount 单笔上限金额
     */
    public void setLimitUpperAmount(BigDecimal limitUpperAmount) {
        this.limitUpperAmount = limitUpperAmount;
    }

    /**
     * 账户最低安全净值
     * @return account_equity_amount 账户最低安全净值
     */
    public BigDecimal getAccountEquityAmount() {
        return accountEquityAmount;
    }

    /**
     * 账户最低安全净值
     * @param accountEquityAmount 账户最低安全净值
     */
    public void setAccountEquityAmount(BigDecimal accountEquityAmount) {
        this.accountEquityAmount = accountEquityAmount;
    }

    /**
     * 账户最低安全净值百分比
     * @return account_equity_percentage 账户最低安全净值百分比
     */
    public BigDecimal getAccountEquityPercentage() {
        return accountEquityPercentage;
    }

    /**
     * 账户最低安全净值百分比
     * @param accountEquityPercentage 账户最低安全净值百分比
     */
    public void setAccountEquityPercentage(BigDecimal accountEquityPercentage) {
        this.accountEquityPercentage = accountEquityPercentage;
    }

    /**
     * 最大跟單金額
     * @return follow_max_amount 最大跟單金額
     */
    public BigDecimal getFollowMaxAmount() {
        return followMaxAmount;
    }

    /**
     * 最大跟單金額
     * @param followMaxAmount 最大跟單金額
     */
    public void setFollowMaxAmount(BigDecimal followMaxAmount) {
        this.followMaxAmount = followMaxAmount;
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
     * 预警损失百分比 （ 0.2 , 0.3）
     * @return follow_alarm_lose 预警损失百分比 （ 0.2 , 0.3）
     */
    public BigDecimal getFollowAlarmLose() {
        return followAlarmLose;
    }

    /**
     * 预警损失百分比 （ 0.2 , 0.3）
     * @param followAlarmLose 预警损失百分比 （ 0.2 , 0.3）
     */
    public void setFollowAlarmLose(BigDecimal followAlarmLose) {
        this.followAlarmLose = followAlarmLose;
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
}