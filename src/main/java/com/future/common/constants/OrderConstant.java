package com.future.common.constants;

/**
 * 平台全局常量类
 */
public class OrderConstant {

	/*order*/
	/**交易中*/
	public static final int ORDER_STATE_TRADING = 0;
	/**已建仓*/
	public static final int ORDER_STATE_OPEN = 1;
	/**部分平仓*/
	public static final int ORDER_STATE_CLOSE_PART = 2;
	/**已关闭*/
	public static final int ORDER_STATE_CLOSE = 3;
}
