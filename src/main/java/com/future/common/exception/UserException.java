package com.future.common.exception;


import com.future.common.enums.ResultCode;
import com.future.common.enums.UserResultCode;

/**
 * 用户模块异常类，用户模块所有业务异常均使用此类处理
 *
 * @author Admin
 * @version: 1.0
 */
public class UserException extends BusinessException {

    public UserException() {
        super(UserResultCode.USER_MODULE_ERROR);
    }

    public UserException(ResultCode resultCode) {
        super(resultCode);
    }

    public UserException(String msg) {
        this();
        super.message = msg;
    }
}