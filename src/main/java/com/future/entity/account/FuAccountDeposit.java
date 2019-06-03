package com.future.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountDeposit {
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
     * 订单ID
     */
    private String orderId;

    /**
     * 存款人ID
     */
    private String depositId;

    /**
     * 存款金额
     */
    private BigDecimal depositAmount;

    /**
     * 存款方式（0 银行卡，1 支付，2微信，3 其他）
     */
    private int depositWay;

    /**
     * 存款类型（0 余额，1 社区币）
     */
    private int depositType;

    /**
     * 存款时间
     */
    private Date depositTime;

    /**
     * 操作前余额
     */
    private BigDecimal accountBefore;

    /**
     * 操作前余额
     */
    private BigDecimal accountAfter;

    /**
     * 存款状态（0 成功，1 失败，2 处理中）
     */
    private int status;

    /**
     * 备注
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
     * 订单ID
     * @return order_id 订单ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 订单ID
     * @param orderId 订单ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 存款人ID
     * @return deposit_id 存款人ID
     */
    public String getDepositId() {
        return depositId;
    }

    /**
     * 存款人ID
     * @param depositId 存款人ID
     */
    public void setDepositId(String depositId) {
        this.depositId = depositId == null ? null : depositId.trim();
    }

    /**
     * 存款金额
     * @return deposit_amount 存款金额
     */
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    /**
     * 存款金额
     * @param depositAmount 存款金额
     */
    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    /**
     * 存款方式（0 银行卡，1 支付，2微信，3 其他）
     * @return deposit_way 存款方式（0 银行卡，1 支付，2微信，3 其他）
     */
    public int getDepositWay() {
        return depositWay;
    }

    /**
     * 存款方式（0 银行卡，1 支付，2微信，3 其他）
     * @param depositWay 存款方式（0 银行卡，1 支付，2微信，3 其他）
     */
    public void setDepositWay(int depositWay) {
        this.depositWay = depositWay;
    }

    /**
     * 存款类型（0 余额，1 社区币）
     * @return deposit_type 存款类型（0 余额，1 社区币）
     */
    public int getDepositType() {
        return depositType;
    }

    /**
     * 存款类型（0 余额，1 社区币）
     * @param depositType 存款类型（0 余额，1 社区币）
     */
    public void setDepositType(int depositType) {
        this.depositType = depositType;
    }

    /**
     * 存款时间
     * @return deposit_time 存款时间
     */
    public Date getDepositTime() {
        return depositTime;
    }

    /**
     * 存款时间
     * @param depositTime 存款时间
     */
    public void setDepositTime(Date depositTime) {
        this.depositTime = depositTime;
    }

    /**
     * 操作前余额
     * @return account_before 操作前余额
     */
    public BigDecimal getAccountBefore() {
        return accountBefore;
    }

    /**
     * 操作前余额
     * @param accountBefore 操作前余额
     */
    public void setAccountBefore(BigDecimal accountBefore) {
        this.accountBefore = accountBefore;
    }

    /**
     * 操作前余额
     * @return account_after 操作前余额
     */
    public BigDecimal getAccountAfter() {
        return accountAfter;
    }

    /**
     * 操作前余额
     * @param accountAfter 操作前余额
     */
    public void setAccountAfter(BigDecimal accountAfter) {
        this.accountAfter = accountAfter;
    }

    /**
     * 存款状态（0 成功，1 失败，2 处理中）
     * @return status 存款状态（0 成功，1 失败，2 处理中）
     */
    public int getStatus() {
        return status;
    }

    /**
     * 存款状态（0 成功，1 失败，2 处理中）
     * @param status 存款状态（0 成功，1 失败，2 处理中）
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
}