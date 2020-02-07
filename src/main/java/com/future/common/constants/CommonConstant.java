package com.future.common.constants;

/**
 * 服务器全局常量类
 */
public class CommonConstant {

	/*服务器*/
	//	服务器状态（0 正常，1 隐藏，2 作废）
	/**信号源状态*/
	public static final int COMMON_SERVER_STATE_NORMAL = 0;
	/**信号源状态*/
	public static final int COMMON_SERVER_STATE_HIDE = 1;
	/**信号源状态*/
	public static final int COMMON_SERVER_STATE_INVALID = 2;
	/**信号源状态*/

	/*是否*/
	public static final int COMMON_YES = 1;
	public static final int COMMON_NO = 0;

	/*审核 （1 通过，0 不通过）*/
	public static final int CHECK_YES = 1;
	public static final int CHECK__NO = 0;


	/*状态 （1 有效，0 无效）*/
	public static final int COMMON_STATE_USABLE = 1;
	public static final int COMMON_STATE_INVALID = 0;


	//	 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
	public static final int APPLY_STATE_DONE = 0;
	public static final int APPLY_STATE_SAVE = 1;
	public static final int APPLY_STATE_CHECK = 2;
	public static final int APPLY_STATE_FAIL = 3;
}
