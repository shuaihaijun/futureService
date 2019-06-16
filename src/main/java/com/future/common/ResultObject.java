package com.future.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by haijun
 * 接口返回公共类
 */
public class ResultObject<T> implements Serializable {

    private static final long serialVersionUID = 3470942433265073054L;
    /**
     * 0 统一表示成功
     * 1 统一表示失败
     * 2 统一表示用户未登陆
     * 其它数值根据自己的业务逻辑枚举
     */
    @JsonProperty("code")
    private String code;
    @JsonProperty("desc")
    private String desc;
    private String redirectUrl;
    private T data;
    private String repeatSubmitToken; // 重复提交令牌

    public ResultObject() {
    }

    public ResultObject(String code) {
        this.setCode(code);
    }

    public ResultObject(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ResultObject(String code, String desc, T object) {
        this.code = code;
        this.desc = desc;
        this.data = object;
    }

    public ResultObject(String code, String desc, String redirectUrl) {
        this.code = code;
        this.desc = desc;
        this.redirectUrl = redirectUrl;
    }

    public void setCode(String code) {
        this.code = code;
        if(code== StatusCode.SUCCESS.getCode()) desc= StatusCode.SUCCESS.getMsg();
        else if(code== StatusCode.DEAL_SUCCESS.getCode()) desc= StatusCode.DEAL_SUCCESS.getMsg();
        else if(code== StatusCode.DEAL_FAIL.getCode()) desc= StatusCode.DEAL_FAIL.getMsg();
        else if(code== StatusCode.DATA_MISS.getCode()) desc= StatusCode.DATA_MISS.getMsg();
        else if(code== StatusCode.FORMAT.getCode()) desc= StatusCode.FORMAT.getMsg();
        else if(code== StatusCode.PARAMETER.getCode()) desc= StatusCode.PARAMETER.getMsg();
        else if(code== StatusCode.AUTHENTICATION.getCode()) desc= StatusCode.AUTHENTICATION.getMsg();
        else if(code== StatusCode.NULL.getCode()) desc= StatusCode.NULL.getMsg();
        else if(code== StatusCode.TYPENONEXIST.getCode()) desc= StatusCode.TYPENONEXIST.getMsg();
        else if(code== StatusCode.OPERNONEXIST.getCode()) desc= StatusCode.OPERNONEXIST.getMsg();
        else if(code== StatusCode.EXCEPTION.getCode()) desc= StatusCode.EXCEPTION.getMsg();
        else if(code== StatusCode.REQUEST.getCode()) desc= StatusCode.REQUEST.getMsg();
        else if(code== StatusCode.FAIL.getCode()) desc= StatusCode.FAIL.getMsg();
        else desc= StatusCode.SUCCESS.getMsg();
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getObject() {
        return data;
    }

    public void setObject(T object) {
        this.data = object;
    }

    public String getRepeatSubmitToken() {
        return repeatSubmitToken;
    }

    public void setRepeatSubmitToken(String repeatSubmitToken) {
        this.repeatSubmitToken = repeatSubmitToken;
    }
}
