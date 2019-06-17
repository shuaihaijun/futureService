package com.future.common.exception;


import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;

/**
 * 数据未找到异常
 *
 * @author Admin
 * @version: 1.0
 */
public class DataNotFoundException extends BusinessException {

    private static final long serialVersionUID = 3721036867889297081L;

    public DataNotFoundException() {
        super(GlobalResultCode.RESULE_DATA_NONE);
    }

    public DataNotFoundException(Object data) {
        this();
        super.data = data;
    }

    public DataNotFoundException(ResultCode resultCode) {
        super(resultCode);
    }

    public DataNotFoundException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public DataNotFoundException(String msg) {
        this();
        super.message = msg;
    }

    public DataNotFoundException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }

}
