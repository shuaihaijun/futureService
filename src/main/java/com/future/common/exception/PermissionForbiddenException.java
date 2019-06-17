package com.future.common.exception;


import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;

/**
 * 权限不足异常
 *
 * @author Admin
 * @version: 1.0
 */
public class PermissionForbiddenException extends BusinessException {

    private static final long serialVersionUID = 3721036867889297081L;

    public PermissionForbiddenException() {
        super(GlobalResultCode.SYSTEM_ILLEGA_ACCESS);
    }

    public PermissionForbiddenException(Object data) {
        this();
        super.data = data;
    }

    public PermissionForbiddenException(ResultCode resultCode) {
        super(resultCode);
    }

    public PermissionForbiddenException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public PermissionForbiddenException(String msg) {
        this();
        super.message = msg;
    }

    public PermissionForbiddenException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }

}
