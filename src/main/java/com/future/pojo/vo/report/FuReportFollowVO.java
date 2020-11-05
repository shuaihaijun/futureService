package com.future.pojo.vo.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信号源信息表
 */
@Data
public class FuReportFollowVO {

    /**
     * 项目工程KEY
     */
    private Integer projKey;

    /**
     * 项目工程名称
     */
    private String projName;
    /**
     * 用户ID
     */
    private Integer userId;
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
     * mt平台账号
     */
    private String mtAccId;
    /**
     * 开始交易时间
     */
    private Date beginDate;
    /**
     * 历史交易天数
     */
    private Integer tradeDaySum;

    /**
     * 交易手数
     */
    private BigDecimal orderLots;

    /**
     * 交易订单数
     */
    private Integer orderCount;

    /**
     * 日均交易手数（该账户交易至今每天的交易标准手数数，扣除节假日）
     */
    private BigDecimal orderLotsDaily;

    /**
     * 日均交易单数（该账户交易至今每天的交易笔数，扣除节假日）
     */
    private BigDecimal orderCountDaily;

    /**
     * 盈利金额（历史盈利金额）
     */
    private BigDecimal orderProfit;

    /**
     * 盈利手数（历史盈利手数）
     */
    private BigDecimal orderProfitLots;

    /**
     * 盈利单数（历史盈利单数）
     */
    private Integer orderProfitCount;

    /**
     * 每笔平均盈利(盈利金额/盈利交易笔数)
     */
    private BigDecimal orderProfitAvg;

    /**
     * 盈利手数占比（盈利手数/总手数）
     */
    private BigDecimal orderProfitRate;
    /**
     * 单笔最大获利
     */
    private BigDecimal orderProfitMax;

    /**
     * 亏损金额（历史亏损金额）
     */
    private BigDecimal orderLoss;

    /**
     * 亏损手数（历史亏损手数）
     */
    private BigDecimal orderLossLots;

    /**
     * 亏损单数（历史亏损单数）
     */
    private Integer orderLossCount;

    /**
     * 每笔平均亏损(亏损金额/亏损交易笔数)
     */
    private BigDecimal orderLossAvg;

    /**
     * 亏损手数占比（亏损手数/总手数）
     */
    private BigDecimal orderLossRate;

    /**
     * 单笔最大亏损
     */
    private BigDecimal orderLossMax;

    /**
     * 最大交易手数
     */
    private BigDecimal orderLotsMax;

    /**
     * 最大交易金额
     */
    private BigDecimal orderMoneyMax;

    /**
     * 最大持仓时间
     */
    private BigDecimal orderHoldTimeMax;

    /**
     * 平均持仓时间
     */
    private BigDecimal orderHoldTimeAvg;

    /**
     * 累计收益
     */
    private BigDecimal orderIncome;

    /**
     * 累计收益率(累计收益/当前净值)
     */
    private BigDecimal orderIncomeRate;

    /**
     * 交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)
     */
    private BigDecimal orderWinRate;

    /**
     * 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好）
     */
    private BigDecimal orderPlRate;
}