package com.future.common.exception;


import com.future.common.enums.ResultCode;
import com.future.common.enums.RmsResultCode;

/**
 * 权限模块异常类，权限模块所有业务异常均使用此类处理
 *
 * @author Admin
 * @version: 1.0
 */
public class RmsrException extends BusinessException {

    public RmsrException() {
        super(RmsResultCode.RMS_MODULE_ERROR);
    }

    public RmsrException(ResultCode resultCode) {
        super(resultCode);
    }

    public RmsrException(String msg) {
        this();
        super.message = msg;
    }
}