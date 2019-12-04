package com.future.common.constants;

/**
 * 用户常亮常量类
 */
public class UserConstant {

	/**
	 * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
	 */
	public static final int USER_TYPE_NORMAL = 0;
	public static final int USER_TYPE_MEMBER_TRIAL = 1;
	public static final int USER_TYPE_MEMBER_NORMAL = 2;
	public static final int USER_TYPE_MEMBER_VIP = 3;
	public static final int USER_TYPE_SALESMAN = 4;
	public static final int USER_TYPE_PIB = 5;
	public static final int USER_TYPE_MIB = 6;
	public static final int USER_TYPE_IB = 7;
	public static final int USER_TYPE_ANALYST = 8;
	public static final int USER_TYPE_DIRECTOR = 9;
	public static final int USER_TYPE_MANAGER = 10;
	public static final int USER_TYPE_SIGNAL = 11;

	/**
	 * 用户状态(0 未审核，1 正常，2 待审核，2 删除）
	 */
	public static final int USER_STATE_UNCHECK = 0;
	public static final int USER_STATE_NORMAL = 1;
	public static final int USER_STATE_PENDING = 2;
	public static final int USER_STATE_DELETE = 3;

}
