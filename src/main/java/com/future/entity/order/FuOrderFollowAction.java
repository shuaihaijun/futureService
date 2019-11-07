package com.future.entity.order;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuOrderFollowAction {
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
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 跟随订单号
     */
    private String userOrderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户服务器ID
     */
    private Integer userServerId;

    /**
     * 用户mt平台账号
     */
    private String userMtAccId;

    /**
     * 信号源订单号
     */
    private String signalOrderId;

    /**
     * 信号源服务器ID
     */
    private Integer signalServerId;

    /**
     * 信号源mt平台账号
     */
    private String signalMtAccId;

    /**
     * 原信号源单号
     */
    private String signalOrderSuperior;

    /**
     * 订单动作（ 0 OPEN ，1 MODIFY,2 CLOSE,3 CLOSE_PARTIAL,4 CLOSE_MAGIC,5 CLOSE_ALL）
     */
    private Integer orderAction;

    /**
     * 跟单状态（0 已完结，1 进行中）
     */
    private Integer orderState;

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
     * 跟随订单号
     * @return user_order_id 跟随订单号
     */
    public String getUserOrderId() {
        return userOrderId;
    }

    /**
     * 跟随订单号
     * @param userOrderId 跟随订单号
     */
    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId == null ? null : userOrderId.trim();
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
     * 用户服务器ID
     * @return user_server_id 用户服务器ID
     */
    public Integer getUserServerId() {
        return userServerId;
    }

    /**
     * 用户服务器ID
     * @param userServerId 用户服务器ID
     */
    public void setUserServerId(Integer userServerId) {
        this.userServerId = userServerId;
    }

    /**
     * 用户mt平台账号
     * @return user_mt_acc_id 用户mt平台账号
     */
    public String getUserMtAccId() {
        return userMtAccId;
    }

    /**
     * 用户mt平台账号
     * @param userMtAccId 用户mt平台账号
     */
    public void setUserMtAccId(String userMtAccId) {
        this.userMtAccId = userMtAccId == null ? null : userMtAccId.trim();
    }

    /**
     * 信号源订单号
     * @return signal_order_id 信号源订单号
     */
    public String getSignalOrderId() {
        return signalOrderId;
    }

    /**
     * 信号源订单号
     * @param signalOrderId 信号源订单号
     */
    public void setSignalOrderId(String signalOrderId) {
        this.signalOrderId = signalOrderId == null ? null : signalOrderId.trim();
    }

    /**
     * 信号源服务器ID
     * @return signal_server_id 信号源服务器ID
     */
    public Integer getSignalServerId() {
        return signalServerId;
    }

    /**
     * 信号源服务器ID
     * @param signalServerId 信号源服务器ID
     */
    public void setSignalServerId(Integer signalServerId) {
        this.signalServerId = signalServerId;
    }

    /**
     * 信号源mt平台账号
     * @return signal_mt_acc_id 信号源mt平台账号
     */
    public String getSignalMtAccId() {
        return signalMtAccId;
    }

    /**
     * 信号源mt平台账号
     * @param signalMtAccId 信号源mt平台账号
     */
    public void setSignalMtAccId(String signalMtAccId) {
        this.signalMtAccId = signalMtAccId == null ? null : signalMtAccId.trim();
    }

    /**
     * 原信号源单号
     * @return signal_order_superior 原信号源单号
     */
    public String getSignalOrderSuperior() {
        return signalOrderSuperior;
    }

    /**
     * 原信号源单号
     * @param signalOrderSuperior 原信号源单号
     */
    public void setSignalOrderSuperior(String signalOrderSuperior) {
        this.signalOrderSuperior = signalOrderSuperior == null ? null : signalOrderSuperior.trim();
    }

    /**
     * 订单动作（ 0 OPEN ，1 MODIFY,2 CLOSE,3 CLOSE_PARTIAL,4 CLOSE_MAGIC,5 CLOSE_ALL）
     * @return order_action 订单动作（ 0 OPEN ，1 MODIFY,2 CLOSE,3 CLOSE_PARTIAL,4 CLOSE_MAGIC,5 CLOSE_ALL）
     */
    public Integer getOrderAction() {
        return orderAction;
    }

    /**
     * 订单动作（ 0 OPEN ，1 MODIFY,2 CLOSE,3 CLOSE_PARTIAL,4 CLOSE_MAGIC,5 CLOSE_ALL）
     * @param orderAction 订单动作（ 0 OPEN ，1 MODIFY,2 CLOSE,3 CLOSE_PARTIAL,4 CLOSE_MAGIC,5 CLOSE_ALL）
     */
    public void setOrderAction(Integer orderAction) {
        this.orderAction = orderAction;
    }

    /**
     * 跟单状态（0 已完结，1 进行中）
     * @return order_state 跟单状态（0 已完结，1 进行中）
     */
    public Integer getOrderState() {
        return orderState;
    }

    /**
     * 跟单状态（0 已完结，1 进行中）
     * @param orderState 跟单状态（0 已完结，1 进行中）
     */
    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}