package com.future.entity.com;

public class FuComCaptcha {
    /**
     * 
     */
    private Long id;

    /**
     * 验证码时间
     */
    private Integer captchaTime;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 内容
     */
    private String word;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 验证码时间
     * @return captcha_time 验证码时间
     */
    public Integer getCaptchaTime() {
        return captchaTime;
    }

    /**
     * 验证码时间
     * @param captchaTime 验证码时间
     */
    public void setCaptchaTime(Integer captchaTime) {
        this.captchaTime = captchaTime;
    }

    /**
     * IP地址
     * @return ip_address IP地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * IP地址
     * @param ipAddress IP地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    /**
     * 内容
     * @return word 内容
     */
    public String getWord() {
        return word;
    }

    /**
     * 内容
     * @param word 内容
     */
    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }
}