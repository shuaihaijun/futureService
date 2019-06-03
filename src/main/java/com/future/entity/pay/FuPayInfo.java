package com.future.entity.pay;

import java.math.BigDecimal;
import java.util.Date;

public class FuPayInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 三方订单ID
     */
    private String payOtherId;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付类型（0 购买产品，1 充值，2 其他）
     */
    private int payType;

    /**
     * 支付方式（0 银行卡，1 支付，2微信，3 其他）
     */
    private int payWay;

    /**
     * 存款时间
     */
    private Date payTime;

    /**
     * 支付状态（0 成功，1 失败，2 处理中）
     */
    private int status;

    /**
     * 备注
     */
    private String comment;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

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
     * 账户ID
     * @return account_id 账户ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账户ID
     * @param accountId 账户ID
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * 三方订单ID
     * @return pay_other_id 三方订单ID
     */
    public String getPayOtherId() {
        return payOtherId;
    }

    /**
     * 三方订单ID
     * @param payOtherId 三方订单ID
     */
    public void setPayOtherId(String payOtherId) {
        this.payOtherId = payOtherId == null ? null : payOtherId.trim();
    }

    /**
     * 支付金额
     * @return pay_amount 支付金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 支付金额
     * @param payAmount 支付金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 支付类型（0 购买产品，1 充值，2 其他）
     * @return pay_type 支付类型（0 购买产品，1 充值，2 其他）
     */
    public int getPayType() {
        return payType;
    }

    /**
     * 支付类型（0 购买产品，1 充值，2 其他）
     * @param payType 支付类型（0 购买产品，1 充值，2 其他）
     */
    public void setPayType(int payType) {
        this.payType = payType;
    }

    /**
     * 支付方式（0 银行卡，1 支付，2微信，3 其他）
     * @return pay_way 支付方式（0 银行卡，1 支付，2微信，3 其他）
     */
    public int getPayWay() {
        return payWay;
    }

    /**
     * 支付方式（0 银行卡，1 支付，2微信，3 其他）
     * @param payWay 支付方式（0 银行卡，1 支付，2微信，3 其他）
     */
    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    /**
     * 存款时间
     * @return pay_time 存款时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 存款时间
     * @param payTime 存款时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 支付状态（0 成功，1 失败，2 处理中）
     * @return status 支付状态（0 成功，1 失败，2 处理中）
     */
    public int getStatus() {
        return status;
    }

    /**
     * 支付状态（0 成功，1 失败，2 处理中）
     * @param status 支付状态（0 成功，1 失败，2 处理中）
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 备注
     * @return comment 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 备注
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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
}