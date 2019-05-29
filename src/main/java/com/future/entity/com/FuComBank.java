package com.future.entity.com;

public class FuComBank {
    /**
     * 
     */
    private Integer id;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行简介
     */
    private String bankSub;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行地址
     */
    private String bankAddress;

    /**
     * 国家
     */
    private Short country;

    /**
     * 省份
     */
    private Short province;

    /**
     * 城市
     */
    private Short city;

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
     * 银行简介
     * @return bank_sub 银行简介
     */
    public String getBankSub() {
        return bankSub;
    }

    /**
     * 银行简介
     * @param bankSub 银行简介
     */
    public void setBankSub(String bankSub) {
        this.bankSub = bankSub == null ? null : bankSub.trim();
    }

    /**
     * 银行代码
     * @return bank_code 银行代码
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 银行代码
     * @param bankCode 银行代码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    /**
     * 银行地址
     * @return bank_address 银行地址
     */
    public String getBankAddress() {
        return bankAddress;
    }

    /**
     * 银行地址
     * @param bankAddress 银行地址
     */
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress == null ? null : bankAddress.trim();
    }

    /**
     * 国家
     * @return country 国家
     */
    public Short getCountry() {
        return country;
    }

    /**
     * 国家
     * @param country 国家
     */
    public void setCountry(Short country) {
        this.country = country;
    }

    /**
     * 省份
     * @return province 省份
     */
    public Short getProvince() {
        return province;
    }

    /**
     * 省份
     * @param province 省份
     */
    public void setProvince(Short province) {
        this.province = province;
    }

    /**
     * 城市
     * @return city 城市
     */
    public Short getCity() {
        return city;
    }

    /**
     * 城市
     * @param city 城市
     */
    public void setCity(Short city) {
        this.city = city;
    }
}