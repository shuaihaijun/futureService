package com.future.common.exception;


import com.future.common.enums.GlobalResultCode;

/**
 * 内部服务异常
 *
 * @author Admin
 * @version: 1.0
 */
public class InternalServerException extends BusinessException {

    private static final long serialVersionUID = 2659909836556958676L;

    /**
     * 根据不同的构造函数，将异常信息进行格式化展示
     */
    public InternalServerException() {
        super(GlobalResultCode.SYSTEM_INNER_ERROR);
    }
}
