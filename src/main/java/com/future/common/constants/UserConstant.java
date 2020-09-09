package com.future.common.constants;

/**
 * 用户常亮常量类
 */
public class UserConstant {

	/**
	 * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
	 */
	public static final int USER_TYPE_NORMAL = 0;
	public static final int USER_TYPE_MEMBER_TRIAL = 1;
	public static final int USER_TYPE_MEMBER_NORMAL = 2;
	public static final int USER_TYPE_MEMBER_VIP = 3;
	public static final int USER_TYPE_SALESMAN = 4;
	public static final int USER_TYPE_IB = 5;
	public static final int USER_TYPE_MIB = 6;
	public static final int USER_TYPE_PIB = 7;
	public static final int USER_TYPE_ANALYST = 8;
	public static final int USER_TYPE_DIRECTOR = 9;
	public static final int USER_TYPE_MANAGER = 10;
	public static final int USER_TYPE_SIGNAL = 11;

	/**
	 * 用户状态(0 正常，1 未审核，2 待审核，2 删除）
	 */
	public static final int USER_STATE_NORMAL = 0;
	public static final int USER_STATE_UNCHECK = 1;
	public static final int USER_STATE_PENDING = 2;
	public static final int USER_STATE_DELETE = 3;

	/**
	 * 用户角色(0 普通用户，1 代理，2 信号源，3 社区管理员，4 系统管理员）
	 */
	public static final int USER_IDENTITY_NORMAL = 0;
	public static final int USER_IDENTITY_AGENT = 1;
	public static final int USER_IDENTITY_SIGNAL = 2;
	public static final int USER_IDENTITY_C_MANAGER = 3;
	public static final int USER_IDENTITY_S_MANAGER = 4;
}
