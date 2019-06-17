package com.future.common.enums;

/**
 * 统一封装响应码和响应消息
 *
 * @author Admin
 * @version: 1.0
 */
public interface ResultCode {

    /**
     * 响应吗
     *
     * @return
     */
    Integer code();

    /**
     * 提示信息
     *
     * @return
     */
    String message();

}