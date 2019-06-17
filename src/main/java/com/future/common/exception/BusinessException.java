package com.future.common.exception;


import com.future.common.enums.BusinessExceptionEnum;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;
import com.future.common.util.StringUtils;

/**
 * 业务异常类，所有业务模块的父类
 *
 * @author Admin
 * @version: 1.0
 */
public class BusinessException extends RuntimeException {


    /**
     * 异常状态码
     */
    protected Integer code;
    /**
     * 异常提示信息
     */
    protected String message;
    /**
     * 异常返回数据状态码  {@link GlobalResultCode 统一状态返回码}
     */
    protected ResultCode resultCode;
    /**
     * 异常数据
     */
    protected Object data;

    /**
     * 无参构造器
     */
    public BusinessException() {
        BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.getByEClass(this.getClass());
        if (exceptionEnum == null) {
            exceptionEnum = BusinessExceptionEnum.INTERNAL_SERVER_ERROR;
        }
        resultCode = exceptionEnum.getResultCode();
        code = exceptionEnum.getResultCode().code();
        message = exceptionEnum.getResultCode().message();

    }

    /**
     * 带有异常提示信息的构造器
     *
     * @param message
     */
    public BusinessException(String message) {
        this();
        this.message = message;
    }

    public BusinessException(Exception exception) {
        super(exception);
        this.message = GlobalResultCode.SYSTEM_INNER_ERROR.message();
        this.code = GlobalResultCode.SYSTEM_INNER_ERROR.code();
        this.resultCode = GlobalResultCode.SYSTEM_INNER_ERROR;
    }

    /**
     * 带有格式化的可变参数构造器
     *
     * @param format
     * @param objects
     */
    public BusinessException(String format, Object... objects) {
        this();
        this.message = StringUtils.formatIfArgs(format, "{}", objects);
    }

    /**
     * 带有统一状态返回码与异常数据的构造器
     *
     * @param resultCode
     * @param data
     */
    public BusinessException(ResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    /**
     * 带有统一状态返回码的构造器
     *
     * @param resultCode
     */
    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    /**
     * 自定义提示信息
     *
     * @param resultCode
     * @param messgae
     */
    public BusinessException(ResultCode resultCode, String messgae) {
        this.resultCode = resultCode;
        this.code = resultCode.code();
        this.message = messgae;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}