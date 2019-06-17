package com.future.common.exception;


import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;

/**
 * 参数无效异常
 *
 * @author Admin
 * @version: 1.0
 */
public class ParameterInvalidException extends BusinessException {

    private static final long serialVersionUID = 3721036867889297081L;

    public ParameterInvalidException() {
        super(GlobalResultCode.PARAM_IS_INVALID);
    }

    public ParameterInvalidException(Object data) {
        this();
        super.data = data;
    }

    public ParameterInvalidException(ResultCode resultCode) {
        super(resultCode);
    }

    public ParameterInvalidException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public ParameterInvalidException(String msg) {
        super(GlobalResultCode.PARAM_IS_INVALID, msg);
    }

    public ParameterInvalidException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }

}
