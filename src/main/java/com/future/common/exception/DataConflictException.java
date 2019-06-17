package com.future.common.exception;


import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;

/**
 * 数据已经存在异常
 *
 * @author Admin
 * @version: 1.0
 */
public class DataConflictException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public DataConflictException() {
        super(GlobalResultCode.DATA_ALREADY_EXISTED);
    }

    public DataConflictException(Object data) {
        this();
        super.data = data;
    }

    public DataConflictException(ResultCode resultCode) {
        super(resultCode);
    }

    public DataConflictException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public DataConflictException(String msg) {
        this();
        super.message = msg;
    }

    public DataConflictException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }


}
