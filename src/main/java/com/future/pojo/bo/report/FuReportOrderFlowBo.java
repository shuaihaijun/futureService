package com.future.pojo.bo.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FuReportOrderFlowBo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * mt平台账号
     */
    private String mtAccId;

    /**
     * 交易时间
     */
    private Date tradeDate;

    /**
     * 交易手数
     */
    private BigDecimal orderLots;

    /**
     * 交易订单数
     */
    private Integer orderCount;

    /**
     * 盈利金额（当日盈利金额）
     */
    private BigDecimal orderProfit;

    /**
     * 盈利手数（当日盈利手数）
     */
    private BigDecimal orderProfitLots;

    /**
     * 盈利单数（当日盈利单数）
     */
    private Integer orderProfitCount;

    /**
     * 每笔平均盈利(盈利金额/盈利交易笔数)
     */
    private BigDecimal orderProfitAvg;

    /**
     * 单笔最大获利
     */
    private BigDecimal orderProfitMax;

    /**
     * 日连续获利金额
     */
    private BigDecimal orderProfitKeep;

    /**
     * 日连续获利天
     */
    private Integer orderProfitKeepCount;

    /**
     * 亏损金额（当日亏损金额）
     */
    private BigDecimal orderLoss;

    /**
     * 亏损手数（当日亏损手数）
     */
    private BigDecimal orderLossLots;

    /**
     * 亏损单数（当日亏损单数）
     */
    private Integer orderLossCount;

    /**
     * 每笔平均亏损(亏损金额/亏损交易笔数)
     */
    private BigDecimal orderLossAvg;

    /**
     * 单笔最大亏损
     */
    private BigDecimal orderLossMax;

    /**
     * 日连续亏损金额
     */
    private BigDecimal orderLossKeep;

    /**
     * 日连亏损利天
     */
    private Integer orderLossKeepCount;

    /**
     * 手续费
     */
    private BigDecimal orderSwap;

    /**
     * 隔夜息
     */
    private BigDecimal orderCommission;

    /**
     * 当日收益
     */
    private BigDecimal orderIncome;

    /**
     * 当日收益率(当日收益/当日净值)
     */
    private BigDecimal orderIncomeRate;

    /**
     * 交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)
     */
    private BigDecimal orderWinRate;

    /**
     * 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好。）
     */
    private BigDecimal orderPlRate;

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
     * 总持仓时间
     */
    private BigDecimal orderHoldTimeSum;

    /**
     * 算法交易笔数
     */
    private Integer orderEaCount;

    /**
     * 算法交易占比
     */
    private BigDecimal orderEaCountRate;

    /**
     * 预期回报（每笔交易平均利润）
     */
    private BigDecimal orderExpectedReturn;

}