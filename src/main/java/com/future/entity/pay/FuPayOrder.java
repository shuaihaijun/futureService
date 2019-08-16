package com.future.entity.pay;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuPayOrder {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 支付ID
     */
    private String payId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 应付金额
     */
    private BigDecimal payAmountPayable;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal payAmountOffer;

    /**
     * 优惠类型（0 奖励，1 活动）
     */
    private int payOfferType;

    /**
     * 优惠ID
     */
    private Integer payOfferId;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付类型（0 购买产品，1 充值，2 购买币）
     */
    private int payType;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private BigDecimal productQuantity;

    /**
     * 商品金额
     */
    private BigDecimal productAmount;

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
     * 支付ID
     * @return pay_id 支付ID
     */
    public String getPayId() {
        return payId;
    }

    /**
     * 支付ID
     * @param payId 支付ID
     */
    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
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
     * 应付金额
     * @return pay_amount_payable 应付金额
     */
    public BigDecimal getPayAmountPayable() {
        return payAmountPayable;
    }

    /**
     * 应付金额
     * @param payAmountPayable 应付金额
     */
    public void setPayAmountPayable(BigDecimal payAmountPayable) {
        this.payAmountPayable = payAmountPayable;
    }

    /**
     * 实付金额
     * @return pay_amount 实付金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 实付金额
     * @param payAmount 实付金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 优惠金额
     * @return pay_amount_offer 优惠金额
     */
    public BigDecimal getPayAmountOffer() {
        return payAmountOffer;
    }

    /**
     * 优惠金额
     * @param payAmountOffer 优惠金额
     */
    public void setPayAmountOffer(BigDecimal payAmountOffer) {
        this.payAmountOffer = payAmountOffer;
    }

    /**
     * 优惠类型（0 奖励，1 活动）
     * @return pay_offer_type 优惠类型（0 奖励，1 活动）
     */
    public int getPayOfferType() {
        return payOfferType;
    }

    /**
     * 优惠类型（0 奖励，1 活动）
     * @param payOfferType 优惠类型（0 奖励，1 活动）
     */
    public void setPayOfferType(int payOfferType) {
        this.payOfferType = payOfferType;
    }

    /**
     * 优惠ID
     * @return pay_offer_id 优惠ID
     */
    public Integer getPayOfferId() {
        return payOfferId;
    }

    /**
     * 优惠ID
     * @param payOfferId 优惠ID
     */
    public void setPayOfferId(Integer payOfferId) {
        this.payOfferId = payOfferId;
    }

    /**
     * 支付时间
     * @return pay_time 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 支付时间
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 支付类型（0 购买产品，1 充值，2 购买币）
     * @return pay_type 支付类型（0 购买产品，1 充值，2 购买币）
     */
    public int getPayType() {
        return payType;
    }

    /**
     * 支付类型（0 购买产品，1 充值，2 购买币）
     * @param payType 支付类型（0 购买产品，1 充值，2 购买币）
     */
    public void setPayType(int payType) {
        this.payType = payType;
    }

    /**
     * 商品ID
     * @return product_id 商品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 商品ID
     * @param productId 商品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 商品价格
     * @return product_price 商品价格
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 商品价格
     * @param productPrice 商品价格
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * 商品数量
     * @return product_quantity 商品数量
     */
    public BigDecimal getProductQuantity() {
        return productQuantity;
    }

    /**
     * 商品数量
     * @param productQuantity 商品数量
     */
    public void setProductQuantity(BigDecimal productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * 商品金额
     * @return product_amount 商品金额
     */
    public BigDecimal getProductAmount() {
        return productAmount;
    }

    /**
     * 商品金额
     * @param productAmount 商品金额
     */
    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
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