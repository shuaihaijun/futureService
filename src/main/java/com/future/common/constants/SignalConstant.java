package com.future.common.constants;

/**
 * 信号源全局常量类
 */
public class SignalConstant {

	/*signal*/
	//	申请状态（0 正常，1 暂存，2 待审核，3 未通过）
	/**信号源状态*/
	public static final int SIGNAL_APPLY_STATE_NORMAL = 0;
	/**信号源状态*/
	public static final int SIGNAL_APPLY_STATE_SAVE = 1;
	/**信号源状态*/
	public static final int SIGNAL_APPLY_STATE_SUBMIT = 2;
	/**信号源状态*/
	public static final int SIGNAL_APPLY_STATE_UNPASS = 3;

	/*signalValuation	*/
	/**信号源默认评级*/
	public static final int SIGNAL_LEVEL_DEFAULT = 1;
	/**信号源默认评级分数*/
	public static final double SIGNAL_SCORE_DEFAULT = 7;
}
