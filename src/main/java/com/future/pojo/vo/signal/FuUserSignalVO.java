package com.future.pojo.vo.signal;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信号源信息表
 */
@Data
public class FuUserSignalVO {
    /**
     * 信号源ID
     */
    private Integer signalId;
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
    private Integer signalLevel;

    /**
     * 信号源描述
     */
    private String signalDesc;
    /**
     * 项目工程KEY
     */
    private Integer projKey;

    /**
     * 项目工程名称
     */
    private String projName;

    /**
     * 申请人ID
     */
    private Integer operUserId;

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
     * 历史最大回撤
     */
    private BigDecimal historyWithdraw;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * mt平台账号
     */
    private String mtAccId;

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
     * 评估时间
     */
    private Date valuationDate;

    /**
     * 评估等级
     */
    private Integer level;

    /**
     * 稳健性
     */
    private BigDecimal steadyScore;

    /**
     * 盈利能力
     */
    private BigDecimal profitScore;

    /**
     * 风控能力
     */
    private BigDecimal riskControlScore;

    /**
     * 资金规模
     */
    private BigDecimal fundSizeScore;

    /**
     * 费侥幸获利
     */
    private BigDecimal nonFlukeProfitScore;

    /**
     * 综合评分
     */
    private BigDecimal score;
    /**
     * 姓名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 介绍人
     */
    private Integer introducer;

    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 推荐人数
     */
    private Integer recommend;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 最大跟单比例
     */
    private BigDecimal followLevel;
    /**
     * 杠杆
     */
    private BigDecimal leverage;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 信用额
     */
    private BigDecimal credit;

    /**
     * 收益
     */
    private BigDecimal profit;

    /**
     * 净值
     */
    private BigDecimal equity;

    /**
     * 保证金
     */
    private BigDecimal margin;

}