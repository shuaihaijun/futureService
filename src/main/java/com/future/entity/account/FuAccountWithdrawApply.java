package com.future.entity.account;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuAccountWithdrawApply {

    public static final String USER_ID = "user_id";
    public static final String APPLY_USER_ID = "apply_user_id";
    public static final String APPLY_STATE = "apply_state";
    public static final String APPLY_DATE = "apply_date";
    public static final String CHECK_DATE = "check_date";

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
     * 账户ID
     */
    private Integer accountId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 申请用户ID
     */
    private Integer applyUserId;

    /**
     * 申请状态（0 正常，1 暂存，2 待审核，3 未通过）
     */
    private Integer applyState;

    /**
     * 回撤类型（0 佣金提取，1 余额提取）
     */
    private Integer withdrawType;

    /**
     * 回撤金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 银行名称 
     */
    private String bankName;

    /**
     * 银行code
     */
    private String bankCode;

    /**
     * 户主姓名
     */
    private String hostName;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 审核时间
     */
    private Date checkDate;

    /**
     * 审批说明
     */
    private String checkDesc;

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
     * 账户ID
     * @return account_id 账户ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 账户ID
     * @param accountId 账户ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 用户账号
     * @return username 用户账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户账号
     * @param username 用户账号
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 昵称
     * @return ref_name 昵称
     */
    public String getRefName() {
        return refName;
    }

    /**
     * 昵称
     * @param refName 昵称
     */
    public void setRefName(String refName) {
        this.refName = refName == null ? null : refName.trim();
    }

    /**
     * 申请用户ID
     * @return apply_user_id 申请用户ID
     */
    public Integer getApplyUserId() {
        return applyUserId;
    }

    /**
     * 申请用户ID
     * @param applyUserId 申请用户ID
     */
    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    /**
     * 申请状态（0 正常，1 暂存，2 待审核，3 未通过）
     * @return apply_state 申请状态（0 正常，1 暂存，2 待审核，3 未通过）
     */
    public Integer getApplyState() {
        return applyState;
    }

    /**
     * 申请状态（0 正常，1 暂存，2 待审核，3 未通过）
     * @param applyState 申请状态（0 正常，1 暂存，2 待审核，3 未通过）
     */
    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    /**
     * 回撤类型（0 佣金提取，1 余额提取）
     * @return withdraw_type 回撤类型（0 佣金提取，1 余额提取）
     */
    public Integer getWithdrawType() {
        return withdrawType;
    }

    /**
     * 回撤类型（0 佣金提取，1 余额提取）
     * @param withdrawType 回撤类型（0 佣金提取，1 余额提取）
     */
    public void setWithdrawType(Integer withdrawType) {
        this.withdrawType = withdrawType;
    }

    /**
     * 回撤金额
     * @return withdraw_amount 回撤金额
     */
    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    /**
     * 回撤金额
     * @param withdrawAmount 回撤金额
     */
    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    /**
     * 银行名称 
     * @return bank_name 银行名称 
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 银行名称 
     * @param bankName 银行名称 
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 银行code
     * @return bank_code 银行code
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 银行code
     * @param bankCode 银行code
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    /**
     * 户主姓名
     * @return host_name 户主姓名
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 户主姓名
     * @param hostName 户主姓名
     */
    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
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