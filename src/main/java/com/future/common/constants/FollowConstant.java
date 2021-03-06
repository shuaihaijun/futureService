package com.future.common.constants;

/**
 * 订单全局常量类
 */
public class FollowConstant {

	/*广播消息标识符*/
	public static final String SUB_MSG="subMsg";
	/*信号源消息标识符*/
	public static final String PULL_MSG="pushMsg";
	/*信号源初始化*/
	public static final String SIGNAL_INIT="SIGNALINIT";
	/*跟随者初始化*/
	public static final String FOLLOW_INIT="FOLLOWINIT";
	/*跟随者交易*/
	public static final String ACTION_TRADE="TRADE";
	/*订单打开标识符*/
	public static final String ACTION_OPEN="OPEN";
	/*订单修改*/
	public static final String ACTION_MODIFY="MODIFY";
	/*订单关闭标识符*/
	public static final String ACTION_CLOSE="CLOSE";
	/*订单部分关闭标识符*/
	public static final String ACTION_CLOSE_PARTIAL="CLOSE_PARTIAL";
	/*订单魔法值关闭*/
	public static final String ACTION_CLOSE_MAGIC="CLOSE_MAGIC";



	//跟单状态（0 正常，1 暂停，2 废弃）
	/*跟单状态 正常*/
	public static final int FOLLOW_STATE_NORMAL=0;
	/*跟单状态 隐藏*/
	public static final int FOLLOW_STATE_HIDE=1;
	/*跟单状态 废弃*/
	public static final int FOLLOW_STATE_DELETE=2;

	/*最大跟单数量*/
	public static final int FOLLOW_MAX_NUM=3;
}
