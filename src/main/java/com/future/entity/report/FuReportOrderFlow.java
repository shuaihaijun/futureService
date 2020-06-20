package com.future.entity.report;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuReportOrderFlow {
    public static String USER_ID="user_id";
    public static String MT_ACC_ID="mt_acc_id";
    public static String TRADE_DATE="trade_date";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
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
     * 交易时间
     * @return trade_date 交易时间
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     * 交易时间
     * @param tradeDate 交易时间
     */
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * 交易手数
     * @return order_lots 交易手数
     */
    public BigDecimal getOrderLots() {
        return orderLots;
    }

    /**
     * 交易手数
     * @param orderLots 交易手数
     */
    public void setOrderLots(BigDecimal orderLots) {
        this.orderLots = orderLots;
    }

    /**
     * 交易订单数
     * @return order_count 交易订单数
     */
    public Integer getOrderCount() {
        return orderCount;
    }

    /**
     * 交易订单数
     * @param orderCount 交易订单数
     */
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * 盈利金额（当日盈利金额）
     * @return order_profit 盈利金额（当日盈利金额）
     */
    public BigDecimal getOrderProfit() {
        return orderProfit;
    }

    /**
     * 盈利金额（当日盈利金额）
     * @param orderProfit 盈利金额（当日盈利金额）
     */
    public void setOrderProfit(BigDecimal orderProfit) {
        this.orderProfit = orderProfit;
    }

    /**
     * 盈利手数（当日盈利手数）
     * @return order_profit_lots 盈利手数（当日盈利手数）
     */
    public BigDecimal getOrderProfitLots() {
        return orderProfitLots;
    }

    /**
     * 盈利手数（当日盈利手数）
     * @param orderProfitLots 盈利手数（当日盈利手数）
     */
    public void setOrderProfitLots(BigDecimal orderProfitLots) {
        this.orderProfitLots = orderProfitLots;
    }

    /**
     * 盈利单数（当日盈利单数）
     * @return order_profit_count 盈利单数（当日盈利单数）
     */
    public Integer getOrderProfitCount() {
        return orderProfitCount;
    }

    /**
     * 盈利单数（当日盈利单数）
     * @param orderProfitCount 盈利单数（当日盈利单数）
     */
    public void setOrderProfitCount(Integer orderProfitCount) {
        this.orderProfitCount = orderProfitCount;
    }

    /**
     * 每笔平均盈利(盈利金额/盈利交易笔数)
     * @return order_profit_avg 每笔平均盈利(盈利金额/盈利交易笔数)
     */
    public BigDecimal getOrderProfitAvg() {
        return orderProfitAvg;
    }

    /**
     * 每笔平均盈利(盈利金额/盈利交易笔数)
     * @param orderProfitAvg 每笔平均盈利(盈利金额/盈利交易笔数)
     */
    public void setOrderProfitAvg(BigDecimal orderProfitAvg) {
        this.orderProfitAvg = orderProfitAvg;
    }

    /**
     * 单笔最大获利
     * @return order_profit_max 单笔最大获利
     */
    public BigDecimal getOrderProfitMax() {
        return orderProfitMax;
    }

    /**
     * 单笔最大获利
     * @param orderProfitMax 单笔最大获利
     */
    public void setOrderProfitMax(BigDecimal orderProfitMax) {
        this.orderProfitMax = orderProfitMax;
    }

    /**
     * 日连续获利金额
     * @return order_profit_keep 日连续获利金额
     */
    public BigDecimal getOrderProfitKeep() {
        return orderProfitKeep;
    }

    /**
     * 日连续获利金额
     * @param orderProfitKeep 日连续获利金额
     */
    public void setOrderProfitKeep(BigDecimal orderProfitKeep) {
        this.orderProfitKeep = orderProfitKeep;
    }

    /**
     * 日连续获利天
     * @return order_profit_keep_count 日连续获利天
     */
    public Integer getOrderProfitKeepCount() {
        return orderProfitKeepCount;
    }

    /**
     * 日连续获利天
     * @param orderProfitKeepCount 日连续获利天
     */
    public void setOrderProfitKeepCount(Integer orderProfitKeepCount) {
        this.orderProfitKeepCount = orderProfitKeepCount;
    }

    /**
     * 亏损金额（当日亏损金额）
     * @return order_loss 亏损金额（当日亏损金额）
     */
    public BigDecimal getOrderLoss() {
        return orderLoss;
    }

    /**
     * 亏损金额（当日亏损金额）
     * @param orderLoss 亏损金额（当日亏损金额）
     */
    public void setOrderLoss(BigDecimal orderLoss) {
        this.orderLoss = orderLoss;
    }

    /**
     * 亏损手数（当日亏损手数）
     * @return order_loss_lots 亏损手数（当日亏损手数）
     */
    public BigDecimal getOrderLossLots() {
        return orderLossLots;
    }

    /**
     * 亏损手数（当日亏损手数）
     * @param orderLossLots 亏损手数（当日亏损手数）
     */
    public void setOrderLossLots(BigDecimal orderLossLots) {
        this.orderLossLots = orderLossLots;
    }

    /**
     * 亏损单数（当日亏损单数）
     * @return order_loss_count 亏损单数（当日亏损单数）
     */
    public Integer getOrderLossCount() {
        return orderLossCount;
    }

    /**
     * 亏损单数（当日亏损单数）
     * @param orderLossCount 亏损单数（当日亏损单数）
     */
    public void setOrderLossCount(Integer orderLossCount) {
        this.orderLossCount = orderLossCount;
    }

    /**
     * 每笔平均亏损(亏损金额/亏损交易笔数)
     * @return order_loss_avg 每笔平均亏损(亏损金额/亏损交易笔数)
     */
    public BigDecimal getOrderLossAvg() {
        return orderLossAvg;
    }

    /**
     * 每笔平均亏损(亏损金额/亏损交易笔数)
     * @param orderLossAvg 每笔平均亏损(亏损金额/亏损交易笔数)
     */
    public void setOrderLossAvg(BigDecimal orderLossAvg) {
        this.orderLossAvg = orderLossAvg;
    }

    /**
     * 单笔最大亏损
     * @return order_loss_max 单笔最大亏损
     */
    public BigDecimal getOrderLossMax() {
        return orderLossMax;
    }

    /**
     * 单笔最大亏损
     * @param orderLossMax 单笔最大亏损
     */
    public void setOrderLossMax(BigDecimal orderLossMax) {
        this.orderLossMax = orderLossMax;
    }

    /**
     * 日连续亏损金额
     * @return order_loss_keep 日连续亏损金额
     */
    public BigDecimal getOrderLossKeep() {
        return orderLossKeep;
    }

    /**
     * 日连续亏损金额
     * @param orderLossKeep 日连续亏损金额
     */
    public void setOrderLossKeep(BigDecimal orderLossKeep) {
        this.orderLossKeep = orderLossKeep;
    }

    /**
     * 日连亏损利天
     * @return order_loss_keep_count 日连亏损利天
     */
    public Integer getOrderLossKeepCount() {
        return orderLossKeepCount;
    }

    /**
     * 日连亏损利天
     * @param orderLossKeepCount 日连亏损利天
     */
    public void setOrderLossKeepCount(Integer orderLossKeepCount) {
        this.orderLossKeepCount = orderLossKeepCount;
    }

    /**
     * 手续费
     * @return order_swap 手续费
     */
    public BigDecimal getOrderSwap() {
        return orderSwap;
    }

    /**
     * 手续费
     * @param orderSwap 手续费
     */
    public void setOrderSwap(BigDecimal orderSwap) {
        this.orderSwap = orderSwap;
    }

    /**
     * 隔夜息
     * @return order_commission 隔夜息
     */
    public BigDecimal getOrderCommission() {
        return orderCommission;
    }

    /**
     * 隔夜息
     * @param orderCommission 隔夜息
     */
    public void setOrderCommission(BigDecimal orderCommission) {
        this.orderCommission = orderCommission;
    }

    /**
     * 当日收益
     * @return order_income 当日收益
     */
    public BigDecimal getOrderIncome() {
        return orderIncome;
    }

    /**
     * 当日收益
     * @param orderIncome 当日收益
     */
    public void setOrderIncome(BigDecimal orderIncome) {
        this.orderIncome = orderIncome;
    }

    /**
     * 当日收益率(当日收益/当日净值)
     * @return order_income_rate 当日收益率(当日收益/当日净值)
     */
    public BigDecimal getOrderIncomeRate() {
        return orderIncomeRate;
    }

    /**
     * 当日收益率(当日收益/当日净值)
     * @param orderIncomeRate 当日收益率(当日收益/当日净值)
     */
    public void setOrderIncomeRate(BigDecimal orderIncomeRate) {
        this.orderIncomeRate = orderIncomeRate;
    }

    /**
     * 交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)
     * @return order_win_rate 交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)
     */
    public BigDecimal getOrderWinRate() {
        return orderWinRate;
    }

    /**
     * 交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)
     * @param orderWinRate 交易胜率(盈利笔数在总笔数中的占比，数据值越大，代表盈利订单占比越高)
     */
    public void setOrderWinRate(BigDecimal orderWinRate) {
        this.orderWinRate = orderWinRate;
    }

    /**
     * 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好。）
     * @return order_pl_rate 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好。）
     */
    public BigDecimal getOrderPlRate() {
        return orderPlRate;
    }

    /**
     * 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好。）
     * @param orderPlRate 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好。）
     */
    public void setOrderPlRate(BigDecimal orderPlRate) {
        this.orderPlRate = orderPlRate;
    }

    /**
     * 最大交易手数
     * @return order_lots_max 最大交易手数
     */
    public BigDecimal getOrderLotsMax() {
        return orderLotsMax;
    }

    /**
     * 最大交易手数
     * @param orderLotsMax 最大交易手数
     */
    public void setOrderLotsMax(BigDecimal orderLotsMax) {
        this.orderLotsMax = orderLotsMax;
    }

    /**
     * 最大交易金额
     * @return order_money_max 最大交易金额
     */
    public BigDecimal getOrderMoneyMax() {
        return orderMoneyMax;
    }

    /**
     * 最大交易金额
     * @param orderMoneyMax 最大交易金额
     */
    public void setOrderMoneyMax(BigDecimal orderMoneyMax) {
        this.orderMoneyMax = orderMoneyMax;
    }

    /**
     * 最大持仓时间
     * @return order_hold_time_max 最大持仓时间
     */
    public BigDecimal getOrderHoldTimeMax() {
        return orderHoldTimeMax;
    }

    /**
     * 最大持仓时间
     * @param orderHoldTimeMax 最大持仓时间
     */
    public void setOrderHoldTimeMax(BigDecimal orderHoldTimeMax) {
        this.orderHoldTimeMax = orderHoldTimeMax;
    }

    /**
     * 平均持仓时间
     * @return order_hold_time_avg 平均持仓时间
     */
    public BigDecimal getOrderHoldTimeAvg() {
        return orderHoldTimeAvg;
    }

    /**
     * 平均持仓时间
     * @param orderHoldTimeAvg 平均持仓时间
     */
    public void setOrderHoldTimeAvg(BigDecimal orderHoldTimeAvg) {
        this.orderHoldTimeAvg = orderHoldTimeAvg;
    }

    /**
     * 算法交易笔数
     * @return order_ea_count 算法交易笔数
     */
    public Integer getOrderEaCount() {
        return orderEaCount;
    }

    /**
     * 算法交易笔数
     * @param orderEaCount 算法交易笔数
     */
    public void setOrderEaCount(Integer orderEaCount) {
        this.orderEaCount = orderEaCount;
    }

    /**
     * 算法交易占比
     * @return order_ea_count_rate 算法交易占比
     */
    public BigDecimal getOrderEaCountRate() {
        return orderEaCountRate;
    }

    /**
     * 算法交易占比
     * @param orderEaCountRate 算法交易占比
     */
    public void setOrderEaCountRate(BigDecimal orderEaCountRate) {
        this.orderEaCountRate = orderEaCountRate;
    }

    /**
     * 预期回报（每笔交易平均利润）
     * @return order_expected_return 预期回报（每笔交易平均利润）
     */
    public BigDecimal getOrderExpectedReturn() {
        return orderExpectedReturn;
    }

    /**
     * 预期回报（每笔交易平均利润）
     * @param orderExpectedReturn 预期回报（每笔交易平均利润）
     */
    public void setOrderExpectedReturn(BigDecimal orderExpectedReturn) {
        this.orderExpectedReturn = orderExpectedReturn;
    }
}