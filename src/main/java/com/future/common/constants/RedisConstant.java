package com.future.common.constants;

/**
 * 订单全局常量类
 */
public class RedisConstant {

	/*mtAccount connect state!!! 此key与tarder项目中的key同步 不可随意修改,这边只取 不存*/
	public static final String H_ACCOUNT_CONNECT_INFO="tradeAccountConnectInfo";
	/*订单 跟随订单信息 此key与tarder项目中的key同步 不可随意修改,这边只取 不存*/
	public static final String L_ORDER_FOLLOW_ORDERS="tradeOrderFollowOrders";
	/*订单 跟随错误 此key与tarder项目中的key同步 不可随意修改,这边只取 不存*/
	public static final String L_ORDER_FOLLOW_ERROR_DATA="tradeOrderFollowErrorData";

	/*订单 跟随订单信息 处理失败暂存*/
	public static final String L_ORDER_FOLLOW_ORDERS_BAK="tradeOrderFollowOrdersBak";
	/*订单 跟随错误 处理失败暂存*/
	public static final String L_ORDER_FOLLOW_ERROR_DATA_BAK="tradeOrderFollowErrorDataBak";

	/*user login token*/
	public static final String H_USER_LOGIN_TOKEN="userLoginToken";

	/*user info*/
	public static final String H_USER_INFO="userInfo";
	/*user project*/
	public static final String H_USER_PROJ="userProj";
	/*user admin*/
	public static final String H_USER_ADMIN="userAdmin";

}
