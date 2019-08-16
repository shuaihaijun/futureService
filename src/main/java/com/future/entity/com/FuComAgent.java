package com.future.entity.com;

import java.util.Date;

public class FuComAgent {

    public static final String ID = "id";
    public static final String AGENT_NAME = "agent_name";
    public static final String AGENT_TYPE = "agent_type";
    public static final String AGENT_LEVEL = "agent_level";
    public static final String AGENT_STATE = "agent_state";
    public static final String USER_ID = "user_id";
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
    private Integer agentLevel;

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
    private Integer agentState;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 说明
     */
    private String comment;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 申请时间
     */
    private Date applyDate;

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
    public Integer getAgentLevel() {
        return agentLevel;
    }

    /**
     * 代理人等级(0 IB ,1 MIB, 2 PIB)
     * @param agentLevel 代理人等级(0 IB ,1 MIB, 2 PIB)
     */
    public void setAgentLevel(Integer agentLevel) {
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
    public Integer getAgentState() {
        return agentState;
    }

    /**
     * 代理人状态
     * @param agentState 代理人状态
     */
    public void setAgentState(Integer agentState) {
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

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 修改时间
     * @return modify_date 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 修改时间
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 申请时间
     * @return apply_date 申请时间
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * 申请时间
     * @param applyDate 申请时间
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}