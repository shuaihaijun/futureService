package com.future.common.constants;

/**
 * 订单全局常量类
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

	//订单动作（0 PositionOpen ，1 PositionClose，2 PositionModify，3 PendingOpen，4 PendingDelete,5 PendingModify,6 PendingFill,7 Balance,8 Credit
	public static final int ORDER_OPERATION_OPEN=0;
	public static final int ORDER_OPERATION_CLOSE=1;
	public static final int ORDER_OPERATION_MODIFY=2;
	/*public static final int ORDER_OPERATION_PendingOpen=3;
	public static final int ORDER_OPERATION_PendingDelete=4;
	public static final int ORDER_OPERATION_PendingModify=5;
	public static final int ORDER_OPERATION_PendingFill=6;
	public static final int ORDER_OPERATION_Balance=7;
	public static final int ORDER_OPERATION_Credit=8;*/

	// enum { O P_BUY=0,OP_SELL,OP_BUY_LIMIT,OP_SELL_LIMIT,OP_BUY_STOP,OP_SELL_STOP,OP_BALANCE,OP_CREDIT };
	//订单类型 OP_BUY = 0， OP_SELL = 1，OP_BUYLIMIT = 2，OP_SELLLIMIT = 3， OP_BUYSTOP = 4，OP_SELLSTOP = 5
	public static final int ORDER_TYPE_BUY=0;
	public static final int ORDER_TYPE_SELL=1;
	public static final int ORDER_TYPE_BUYLIMIT=2;
	public static final int ORDER_TYPE_SELLLIMIT=3;
	public static final int ORDER_TYPE_BUYSTOP=4;
	public static final int ORDER_TYPE_SELLSTOP=5;
	public static final int ORDER_TYPE_BALANCE=6;
	public static final int ORDER_TYPE_DEPOSIT=7;

	//跟单状态（0 已完结，1 进行中）
	public static final int ORDER_FOLLOW_ORDERS_STATE_DONE=0;
	public static final int ORDER_FOLLOW_ORDERS_STATE_ING=1;
}
