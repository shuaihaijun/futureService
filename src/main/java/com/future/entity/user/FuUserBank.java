package com.future.entity.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuUserBank {


    public static String Bank_ID="id";
    public static String USER_ID="user_id";
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 户主姓名
     */
    private String hostName;

    /**
     * 银行卡号
     */
    private String code;

    /**
     * 银行卡 路径
     */
    private String cardAdress;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 修改日期
     */
    private Date modifyTime;

    /**
     * 是否为主账号(1 是，0 否)
     */
    private Integer isChief;

    /**
     * 是否已校验（0 否，1 是）
     */
    private Integer isChecked;

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
     * 银行卡号
     * @return code 银行卡号
     */
    public String getCode() {
        return code;
    }

    /**
     * 银行卡号
     * @param code 银行卡号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 银行卡 路径
     * @return card_adress 银行卡 路径
     */
    public String getCardAdress() {
        return cardAdress;
    }

    /**
     * 银行卡 路径
     * @param cardAdress 银行卡 路径
     */
    public void setCardAdress(String cardAdress) {
        this.cardAdress = cardAdress == null ? null : cardAdress.trim();
    }

    /**
     * 创建日期
     * @return create_time 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建日期
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改日期
     * @return modify_time 修改日期
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改日期
     * @param modifyTime 修改日期
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 是否为主账号(1 是，0 否)
     * @return is_chief 是否为主账号(1 是，0 否)
     */
    public Integer getIsChief() {
        return isChief;
    }

    /**
     * 是否为主账号(1 是，0 否)
     * @param isChief 是否为主账号(1 是，0 否)
     */
    public void setIsChief(Integer isChief) {
        this.isChief = isChief;
    }

    /**
     * 是否已校验（0 否，1 是）
     * @return is_checked 是否已校验（0 否，1 是）
     */
    public Integer getIsChecked() {
        return isChecked;
    }

    /**
     * 是否已校验（0 否，1 是）
     * @param isChecked 是否已校验（0 否，1 是）
     */
    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
}