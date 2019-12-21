package com.future.common.constants;

/**
 * 代理全局常量类
 */
public class AgentConstant {

	/*agent*/
	//	 申请类型（0 注册申请，1 IB升级申请 ，2 IB降级申请）
	public static final int AGENT_APPLY_TYPE_NEW = 0;
	public static final int AGENT_APPLY_TYPE_UP = 1;
	public static final int AGENT_APPLY_TYPE_DOWN = 2;

	//	 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
	public static final int AGENT_APPLY_STATE_DONE = 0;
	public static final int AGENT_APPLY_STATE_SAVE = 1;
	public static final int AGENT_APPLY_STATE_CHECK = 2;
	public static final int AGENT_APPLY_STATE_FAIL = 3;

	//代理人状态(0 正常，1 待审核，2 隐藏，3 删除)
	public static final int AGENT_STATE_NORMAL = 0;
	public static final int AGENT_STATE_FREEZE = 1;
	public static final int AGENT_STATE_HIDE = 2;
	public static final int AGENT_STATE_REMOVE = 3;

	public static final int AGENT_TYPE_IB = 5;
	public static final int AGENT_TYPE_MIB = 6;
	public static final int AGENT_TYPE_PIB = 7;
}
