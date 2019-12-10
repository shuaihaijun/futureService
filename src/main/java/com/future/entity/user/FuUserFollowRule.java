package com.future.entity.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuUserFollowRule {
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
     * 用户服务器ID
     */
    private Integer userServerId;

    /**
     * 用户mt平台账号
     */
    private String userMtAccId;

    /**
     * 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     */
    private Byte ruleType;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 规则状态（0 正常，1  删除）
     */
    private Byte ruleState;

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
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

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
     * 用户服务器ID
     * @return user_server_id 用户服务器ID
     */
    public Integer getUserServerId() {
        return userServerId;
    }

    /**
     * 用户服务器ID
     * @param userServerId 用户服务器ID
     */
    public void setUserServerId(Integer userServerId) {
        this.userServerId = userServerId;
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
     * 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     * @return rule_type 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     */
    public Byte getRuleType() {
        return ruleType;
    }

    /**
     * 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     * @param ruleType 规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
     */
    public void setRuleType(Byte ruleType) {
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
     * 规则状态（0 正常，1  删除）
     * @return rule_state 规则状态（0 正常，1  删除）
     */
    public Byte getRuleState() {
        return ruleState;
    }

    /**
     * 规则状态（0 正常，1  删除）
     * @param ruleState 规则状态（0 正常，1  删除）
     */
    public void setRuleState(Byte ruleState) {
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