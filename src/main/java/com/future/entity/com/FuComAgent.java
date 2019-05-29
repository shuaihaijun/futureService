package com.future.entity.com;

public class FuComAgent {
    /**
     * 
     */
    private Integer id;

    /**
     * 代理人名称
     */
    private String agentName;

    /**
     * IB类型（IB ,MIB,PIB）
     */
    private String agentType;

    /**
     * 代理人等级(0 IB ,1 MIB, 2 PIB)
     */
    private Byte agentLevel;

    /**
     * 代理描述
     */
    private String applyDesc;

    /**
     * 代理人数
     */
    private Integer agentNumber;

    /**
     * 代理人状态
     */
    private Byte agentState;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 说明
     */
    private String comment;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 代理人名称
     * @return agent_name 代理人名称
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 代理人名称
     * @param agentName 代理人名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    /**
     * IB类型（IB ,MIB,PIB）
     * @return agent_type IB类型（IB ,MIB,PIB）
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * IB类型（IB ,MIB,PIB）
     * @param agentType IB类型（IB ,MIB,PIB）
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType == null ? null : agentType.trim();
    }

    /**
     * 代理人等级(0 IB ,1 MIB, 2 PIB)
     * @return agent_level 代理人等级(0 IB ,1 MIB, 2 PIB)
     */
    public Byte getAgentLevel() {
        return agentLevel;
    }

    /**
     * 代理人等级(0 IB ,1 MIB, 2 PIB)
     * @param agentLevel 代理人等级(0 IB ,1 MIB, 2 PIB)
     */
    public void setAgentLevel(Byte agentLevel) {
        this.agentLevel = agentLevel;
    }

    /**
     * 代理描述
     * @return apply_desc 代理描述
     */
    public String getApplyDesc() {
        return applyDesc;
    }

    /**
     * 代理描述
     * @param applyDesc 代理描述
     */
    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc == null ? null : applyDesc.trim();
    }

    /**
     * 代理人数
     * @return agent_number 代理人数
     */
    public Integer getAgentNumber() {
        return agentNumber;
    }

    /**
     * 代理人数
     * @param agentNumber 代理人数
     */
    public void setAgentNumber(Integer agentNumber) {
        this.agentNumber = agentNumber;
    }

    /**
     * 代理人状态
     * @return agent_state 代理人状态
     */
    public Byte getAgentState() {
        return agentState;
    }

    /**
     * 代理人状态
     * @param agentState 代理人状态
     */
    public void setAgentState(Byte agentState) {
        this.agentState = agentState;
    }

    /**
     * 分组名称
     * @return group_name 分组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 分组名称
     * @param groupName 分组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * 说明
     * @return comment 说明
     */
    public String getComment() {
        return comment;
    }

    /**
     * 说明
     * @param comment 说明
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}