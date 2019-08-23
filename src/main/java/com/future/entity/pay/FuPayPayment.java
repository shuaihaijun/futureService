package com.future.entity.pay;

public class FuPayPayment {
    /**
     * 
     */
    private Integer id;

    /**
     * 支付码
     */
    private String code;

    /**
     * 用户
     */
    private Integer user;

    /**
     * 姓名
     */
    private String name;

    /**
     * 贸易商
     */
    private String merchant;

    /**
     * 是否可用
     */
    private Integer enabled;

    /**
     * 秘钥
     */
    private String secret;

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
     * 支付码
     * @return code 支付码
     */
    public String getCode() {
        return code;
    }

    /**
     * 支付码
     * @param code 支付码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 用户
     * @return user 用户
     */
    public Integer getUser() {
        return user;
    }

    /**
     * 用户
     * @param user 用户
     */
    public void setUser(Integer user) {
        this.user = user;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 贸易商
     * @return merchant 贸易商
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * 贸易商
     * @param merchant 贸易商
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant == null ? null : merchant.trim();
    }

    /**
     * 是否可用
     * @return enabled 是否可用
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * 是否可用
     * @param enabled 是否可用
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * 秘钥
     * @return secret 秘钥
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 秘钥
     * @param secret 秘钥
     */
    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }
}