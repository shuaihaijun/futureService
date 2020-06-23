package com.future.entity.report;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuReportOrderSum {
    public static String USER_ID="user_id";
    public static String MT_ACC_ID="mt_acc_id";
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
     * 开始交易时间
     */
    private Date beginDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

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
     * 日连续最大获利金额
     */
    private BigDecimal orderProfitKeep;

    /**
     * 日连续最大获利天
     */
    private Integer orderProfitKeepCount;

    /**
     * 日连续最大获利最后时间
     */
    private Date orderProfitKeepDay;

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
     * 日连续亏损金额
     */
    private BigDecimal orderLossKeep;

    /**
     * 日连亏损利天
     */
    private Integer orderLossKeepCount;

    /**
     * 日连续最大亏损最后时间
     */
    private Date orderLossKeepDay;

    /**
     * 算法交易笔数
     */
    private Integer orderEaCount;

    /**
     * 算法交易占比
     */
    private BigDecimal orderEaCountRate;

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
     * 累计手续费
     */
    private BigDecimal orderSwap;

    /**
     * 累计隔夜利息
     */
    private BigDecimal orderCommission;

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
     * 开始交易时间
     * @return begin_date 开始交易时间
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * 开始交易时间
     * @param beginDate 开始交易时间
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
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
     * 历史交易天数
     * @return trade_day_sum 历史交易天数
     */
    public Integer getTradeDaySum() {
        return tradeDaySum;
    }

    /**
     * 历史交易天数
     * @param tradeDaySum 历史交易天数
     */
    public void setTradeDaySum(Integer tradeDaySum) {
        this.tradeDaySum = tradeDaySum;
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
     * 日均交易手数（该账户交易至今每天的交易标准手数数，扣除节假日）
     * @return order_lots_daily 日均交易手数（该账户交易至今每天的交易标准手数数，扣除节假日）
     */
    public BigDecimal getOrderLotsDaily() {
        return orderLotsDaily;
    }

    /**
     * 日均交易手数（该账户交易至今每天的交易标准手数数，扣除节假日）
     * @param orderLotsDaily 日均交易手数（该账户交易至今每天的交易标准手数数，扣除节假日）
     */
    public void setOrderLotsDaily(BigDecimal orderLotsDaily) {
        this.orderLotsDaily = orderLotsDaily;
    }

    /**
     * 日均交易单数（该账户交易至今每天的交易笔数，扣除节假日）
     * @return order_count_daily 日均交易单数（该账户交易至今每天的交易笔数，扣除节假日）
     */
    public BigDecimal getOrderCountDaily() {
        return orderCountDaily;
    }

    /**
     * 日均交易单数（该账户交易至今每天的交易笔数，扣除节假日）
     * @param orderCountDaily 日均交易单数（该账户交易至今每天的交易笔数，扣除节假日）
     */
    public void setOrderCountDaily(BigDecimal orderCountDaily) {
        this.orderCountDaily = orderCountDaily;
    }

    /**
     * 盈利金额（历史盈利金额）
     * @return order_profit 盈利金额（历史盈利金额）
     */
    public BigDecimal getOrderProfit() {
        return orderProfit;
    }

    /**
     * 盈利金额（历史盈利金额）
     * @param orderProfit 盈利金额（历史盈利金额）
     */
    public void setOrderProfit(BigDecimal orderProfit) {
        this.orderProfit = orderProfit;
    }

    /**
     * 盈利手数（历史盈利手数）
     * @return order_profit_lots 盈利手数（历史盈利手数）
     */
    public BigDecimal getOrderProfitLots() {
        return orderProfitLots;
    }

    /**
     * 盈利手数（历史盈利手数）
     * @param orderProfitLots 盈利手数（历史盈利手数）
     */
    public void setOrderProfitLots(BigDecimal orderProfitLots) {
        this.orderProfitLots = orderProfitLots;
    }

    /**
     * 盈利单数（历史盈利单数）
     * @return order_profit_count 盈利单数（历史盈利单数）
     */
    public Integer getOrderProfitCount() {
        return orderProfitCount;
    }

    /**
     * 盈利单数（历史盈利单数）
     * @param orderProfitCount 盈利单数（历史盈利单数）
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
     * 盈利手数占比（盈利手数/总手数）
     * @return order_profit_rate 盈利手数占比（盈利手数/总手数）
     */
    public BigDecimal getOrderProfitRate() {
        return orderProfitRate;
    }

    /**
     * 盈利手数占比（盈利手数/总手数）
     * @param orderProfitRate 盈利手数占比（盈利手数/总手数）
     */
    public void setOrderProfitRate(BigDecimal orderProfitRate) {
        this.orderProfitRate = orderProfitRate;
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
     * 日连续最大获利金额
     * @return order_profit_keep 日连续最大获利金额
     */
    public BigDecimal getOrderProfitKeep() {
        return orderProfitKeep;
    }

    /**
     * 日连续最大获利金额
     * @param orderProfitKeep 日连续最大获利金额
     */
    public void setOrderProfitKeep(BigDecimal orderProfitKeep) {
        this.orderProfitKeep = orderProfitKeep;
    }

    /**
     * 日连续最大获利天
     * @return order_profit_keep_count 日连续最大获利天
     */
    public Integer getOrderProfitKeepCount() {
        return orderProfitKeepCount;
    }

    /**
     * 日连续最大获利天
     * @param orderProfitKeepCount 日连续最大获利天
     */
    public void setOrderProfitKeepCount(Integer orderProfitKeepCount) {
        this.orderProfitKeepCount = orderProfitKeepCount;
    }

    /**
     * 日连续最大获利最后时间
     * @return order_profit_keep_day 日连续最大获利最后时间
     */
    public Date getOrderProfitKeepDay() {
        return orderProfitKeepDay;
    }

    /**
     * 日连续最大获利最后时间
     * @param orderProfitKeepDay 日连续最大获利最后时间
     */
    public void setOrderProfitKeepDay(Date orderProfitKeepDay) {
        this.orderProfitKeepDay = orderProfitKeepDay;
    }

    /**
     * 亏损金额（历史亏损金额）
     * @return order_loss 亏损金额（历史亏损金额）
     */
    public BigDecimal getOrderLoss() {
        return orderLoss;
    }

    /**
     * 亏损金额（历史亏损金额）
     * @param orderLoss 亏损金额（历史亏损金额）
     */
    public void setOrderLoss(BigDecimal orderLoss) {
        this.orderLoss = orderLoss;
    }

    /**
     * 亏损手数（历史亏损手数）
     * @return order_loss_lots 亏损手数（历史亏损手数）
     */
    public BigDecimal getOrderLossLots() {
        return orderLossLots;
    }

    /**
     * 亏损手数（历史亏损手数）
     * @param orderLossLots 亏损手数（历史亏损手数）
     */
    public void setOrderLossLots(BigDecimal orderLossLots) {
        this.orderLossLots = orderLossLots;
    }

    /**
     * 亏损单数（历史亏损单数）
     * @return order_loss_count 亏损单数（历史亏损单数）
     */
    public Integer getOrderLossCount() {
        return orderLossCount;
    }

    /**
     * 亏损单数（历史亏损单数）
     * @param orderLossCount 亏损单数（历史亏损单数）
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
     * 亏损手数占比（亏损手数/总手数）
     * @return order_loss_rate 亏损手数占比（亏损手数/总手数）
     */
    public BigDecimal getOrderLossRate() {
        return orderLossRate;
    }

    /**
     * 亏损手数占比（亏损手数/总手数）
     * @param orderLossRate 亏损手数占比（亏损手数/总手数）
     */
    public void setOrderLossRate(BigDecimal orderLossRate) {
        this.orderLossRate = orderLossRate;
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
     * 日连续最大亏损最后时间
     * @return order_loss_keep_day 日连续最大亏损最后时间
     */
    public Date getOrderLossKeepDay() {
        return orderLossKeepDay;
    }

    /**
     * 日连续最大亏损最后时间
     * @param orderLossKeepDay 日连续最大亏损最后时间
     */
    public void setOrderLossKeepDay(Date orderLossKeepDay) {
        this.orderLossKeepDay = orderLossKeepDay;
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
     * 累计手续费
     * @return order_swap 累计手续费
     */
    public BigDecimal getOrderSwap() {
        return orderSwap;
    }

    /**
     * 累计手续费
     * @param orderSwap 累计手续费
     */
    public void setOrderSwap(BigDecimal orderSwap) {
        this.orderSwap = orderSwap;
    }

    /**
     * 累计隔夜利息
     * @return order_commission 累计隔夜利息
     */
    public BigDecimal getOrderCommission() {
        return orderCommission;
    }

    /**
     * 累计隔夜利息
     * @param orderCommission 累计隔夜利息
     */
    public void setOrderCommission(BigDecimal orderCommission) {
        this.orderCommission = orderCommission;
    }

    /**
     * 累计收益
     * @return order_income 累计收益
     */
    public BigDecimal getOrderIncome() {
        return orderIncome;
    }

    /**
     * 累计收益
     * @param orderIncome 累计收益
     */
    public void setOrderIncome(BigDecimal orderIncome) {
        this.orderIncome = orderIncome;
    }

    /**
     * 累计收益率(累计收益/当前净值)
     * @return order_income_rate 累计收益率(累计收益/当前净值)
     */
    public BigDecimal getOrderIncomeRate() {
        return orderIncomeRate;
    }

    /**
     * 累计收益率(累计收益/当前净值)
     * @param orderIncomeRate 累计收益率(累计收益/当前净值)
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
     * 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好）
     * @return order_pl_rate 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好）
     */
    public BigDecimal getOrderPlRate() {
        return orderPlRate;
    }

    /**
     * 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好）
     * @param orderPlRate 净值盈亏比（盈利订单总额与亏损订单总额的比值，该值越大表明该账户当前的绩效结果越好）
     */
    public void setOrderPlRate(BigDecimal orderPlRate) {
        this.orderPlRate = orderPlRate;
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