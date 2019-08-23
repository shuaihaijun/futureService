package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountTransfer {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 转移类型（0 佣金提取，1 虚拟币兑换）
     */
    private Integer transferType;

    /**
     * 转移来源
     */
    private String transferFrom;

    /**
     * 转移目标
     */
    private String transferTo;

    /**
     * 转移金额
     */
    private BigDecimal transferAmount;

    /**
     * 转移时间
     */
    private Date transferTime;

    /**
     * 转移状态（0 成功，1 失败，2 执行中）
     */
    private Integer transferState;

    /**
     * 详情
     */
    private String params;

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
     * 转移类型（0 佣金提取，1 虚拟币兑换）
     * @return transfer_type 转移类型（0 佣金提取，1 虚拟币兑换）
     */
    public Integer getTransferType() {
        return transferType;
    }

    /**
     * 转移类型（0 佣金提取，1 虚拟币兑换）
     * @param transferType 转移类型（0 佣金提取，1 虚拟币兑换）
     */
    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    /**
     * 转移来源
     * @return transfer_from 转移来源
     */
    public String getTransferFrom() {
        return transferFrom;
    }

    /**
     * 转移来源
     * @param transferFrom 转移来源
     */
    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom == null ? null : transferFrom.trim();
    }

    /**
     * 转移目标
     * @return transfer_to 转移目标
     */
    public String getTransferTo() {
        return transferTo;
    }

    /**
     * 转移目标
     * @param transferTo 转移目标
     */
    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo == null ? null : transferTo.trim();
    }

    /**
     * 转移金额
     * @return transfer_amount 转移金额
     */
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    /**
     * 转移金额
     * @param transferAmount 转移金额
     */
    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    /**
     * 转移时间
     * @return transfer_time 转移时间
     */
    public Date getTransferTime() {
        return transferTime;
    }

    /**
     * 转移时间
     * @param transferTime 转移时间
     */
    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    /**
     * 转移状态（0 成功，1 失败，2 执行中）
     * @return transfer_state 转移状态（0 成功，1 失败，2 执行中）
     */
    public Integer getTransferState() {
        return transferState;
    }

    /**
     * 转移状态（0 成功，1 失败，2 执行中）
     * @param transferState 转移状态（0 成功，1 失败，2 执行中）
     */
    public void setTransferState(Integer transferState) {
        this.transferState = transferState;
    }

    /**
     * 详情
     * @return params 详情
     */
    public String getParams() {
        return params;
    }

    /**
     * 详情
     * @param params 详情
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }
}