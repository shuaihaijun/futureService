package com.future.common.constants;

import java.math.BigDecimal;

/**
 * 信号源全局常量类
 */
public class CommissionConstant {

	//	佣金类型（0 信号源返佣，1 多级返佣，2 社区返佣，3 其他）
	/**佣金类型*/
	public static final int COMMISSION_TYPE_SIGNAL = 0;
	/**佣金类型*/
	public static final int COMMISSION_TYPE_AGENT = 1;
	/**佣金类型*/
	public static final int COMMISSION_TYPE_COMMUNITY = 2;
	/**佣金类型*/
	public static final int COMMISSION_TYPE_OTHER = 3;

	/**
	 * 收佣等级（1级、2级、3级）
	 */
	public static final int COMMISSION_USER_LEVEL_ZERO = 0;
	public static final int COMMISSION_USER_LEVEL_FIRST = 1;
	public static final int COMMISSION_USER_LEVEL_SECOND = 2;
	public static final int COMMISSION_USER_LEVEL_THIRD = 3;

	/*订单类型（0 社区跟单，1 自主交易）*/
	public static final int COMMISSION_ORDER_TYPE_FOLLOW = 0;
	public static final int COMMISSION_ORDER_TYPE_CUSTOMER = 1;

	/*比率计算类型（0 交易手数，1 按原金额，2 按返佣金额, 3 指定金额）*/
	public static final int COMMISSION_RATE_TYPE_LOTS = 0;
	public static final int COMMISSION_RATE_TYPE_MONEY_SOURCE = 1;
	public static final int COMMISSION_RATE_TYPE_MONEY_COMMISSION = 2;
	public static final int COMMISSION_RATE_TYPE_MONEY= 3;

	/*commissionState 返佣状态（0 成功，1失败，2 执行中）*/
	public static final int COMMISSION_STATE_SUCCESS = 0;
	public static final int COMMISSION_STATE_FAIL = 2;
	public static final int COMMISSION_STATE_ING = 3;

	/**
	 * 回撤类型（0 佣金提取，1 余额提取）
	 */
	public static final int COMMISSION_WITHDRAW_TYPE_COMMISSION= 0;
	public static final int COMMISSION_WITHDRAW_TYPE_BALANCE= 1;

	/*coin_type 币种类型（0 余额，1社区币，2 期债）*/
	/*社区同级别代理返佣*/
	public static final BigDecimal COMMISSION_SAME_LEVEL_RATE_FROM_COMMUNITY= new BigDecimal(0.1);
}
