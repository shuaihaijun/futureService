package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuComAgentApply {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请类型（0 IB升级申请，1 注册申请 ，2 IB降级申请）
     */
    private int applyType;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 代理人ID
     */
    private Integer agentId;

    /**
     * 当前等级
     */
    private int agentLevel;

    /**
     * 申请级别
     */
    private int applyLevel;

    /**
     * 申请原由
     */
    private String applyReason;

    /**
     * 代理描述
     */
    private String applyDesc;

    /**
     * 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
     */
    private int applyState;

    /**
     * 审批说明
     */
    private String checkDesc;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 审核时间
     */
    private Date checkDate;

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
     * 申请类型（0 IB升级申请，1 注册申请 ，2 IB降级申请）
     * @return apply_type 申请类型（0 IB升级申请，1 注册申请 ，2 IB降级申请）
     */
    public int getApplyType() {
        return applyType;
    }

    /**
     * 申请类型（0 IB升级申请，1 注册申请 ，2 IB降级申请）
     * @param applyType 申请类型（0 IB升级申请，1 注册申请 ，2 IB降级申请）
     */
    public void setApplyType(int applyType) {
        this.applyType = applyType;
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
     * 代理人ID
     * @return agent_id 代理人ID
     */
    public Integer getAgentId() {
        return agentId;
    }

    /**
     * 代理人ID
     * @param agentId 代理人ID
     */
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    /**
     * 当前等级
     * @return agent_level 当前等级
     */
    public int getAgentLevel() {
        return agentLevel;
    }

    /**
     * 当前等级
     * @param agentLevel 当前等级
     */
    public void setAgentLevel(int agentLevel) {
        this.agentLevel = agentLevel;
    }

    /**
     * 申请级别
     * @return apply_level 申请级别
     */
    public int getApplyLevel() {
        return applyLevel;
    }

    /**
     * 申请级别
     * @param applyLevel 申请级别
     */
    public void setApplyLevel(int applyLevel) {
        this.applyLevel = applyLevel;
    }

    /**
     * 申请原由
     * @return apply_reason 申请原由
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     * 申请原由
     * @param applyReason 申请原由
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason == null ? null : applyReason.trim();
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
     * 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
     * @return apply_state 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
     */
    public int getApplyState() {
        return applyState;
    }

    /**
     * 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
     * @param applyState 申请状态（ 0 完成，1 暂存，2 待审，3 未通过）
     */
    public void setApplyState(int applyState) {
        this.applyState = applyState;
    }

    /**
     * 审批说明
     * @return check_desc 审批说明
     */
    public String getCheckDesc() {
        return checkDesc;
    }

    /**
     * 审批说明
     * @param checkDesc 审批说明
     */
    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc == null ? null : checkDesc.trim();
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

    /**
     * 审核时间
     * @return check_date 审核时间
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     * 审核时间
     * @param checkDate 审核时间
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}