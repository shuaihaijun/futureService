package com.future.common.enums;

import com.future.common.exception.*;
import org.springframework.http.HttpStatus;


/**
 * 异常、HTTP状态码、默认自定义返回码 映射类
 *
 * @author Admin
 * @version: 1.0
 */
public enum BusinessExceptionEnum {

    /**
     * 无效参数
     */
    PARAMETER_INVALID(ParameterInvalidException.class, HttpStatus.BAD_REQUEST, GlobalResultCode.PARAM_IS_INVALID),

    /**
     * 数据未找到
     */
    NOT_FOUND(DataNotFoundException.class, HttpStatus.NOT_FOUND, GlobalResultCode.RESULE_DATA_NONE),

    /**
     * 数据已存在
     */
    CONFLICT(DataConflictException.class, HttpStatus.CONFLICT, GlobalResultCode.DATA_ALREADY_EXISTED),

    /**
     * 无访问权限
     */
    FORBIDDEN(PermissionForbiddenException.class, HttpStatus.FORBIDDEN, GlobalResultCode.SYSTEM_ILLEGA_ACCESS),

    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR(InternalServerException.class, HttpStatus.INTERNAL_SERVER_ERROR, GlobalResultCode.SYSTEM_INNER_ERROR);

    /**
     * 返回结果状态码
     */
    private ResultCode resultCode;
    /**
     * http状态码,spring定义
     */
    private HttpStatus httpStatus;
    /**
     * 异常类型
     */
    private Class<? extends BusinessException> eClass;


    BusinessExceptionEnum(Class<? extends BusinessException> eClass, HttpStatus httpStatus, ResultCode resultCode) {
        this.eClass = eClass;
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
    }

    public Class<? extends BusinessException> getEClass() {
        return eClass;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    /**
     * 判断是否支持HttpStatus状态码
     *
     * @param httpStatus
     * @return
     */
    public static boolean isSupportHttpStatus(int httpStatus) {
        for (BusinessExceptionEnum exceptionEnum : BusinessExceptionEnum.values()) {
            if (exceptionEnum.httpStatus.value() == httpStatus) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是支持的异常类型
     *
     * @param z
     * @return
     */
    public static boolean isSupportException(Class<?> z) {
        for (BusinessExceptionEnum exceptionEnum : BusinessExceptionEnum.values()) {
            if (exceptionEnum.eClass.equals(z)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据状态码获取异常的枚举信息
     *
     * @param httpStatus
     * @return
     */
    public static BusinessExceptionEnum getByHttpStatus(HttpStatus httpStatus) {
        if (httpStatus == null) {
            return null;
        }

        for (BusinessExceptionEnum exceptionEnum : BusinessExceptionEnum.values()) {
            if (httpStatus.equals(exceptionEnum.httpStatus)) {
                return exceptionEnum;
            }
        }
        return null;
    }

    /**
     * 根据异常类型获取异常的枚举信息
     *
     * @param eClass
     * @return
     */
    public static BusinessExceptionEnum getByEClass(Class<? extends BusinessException> eClass) {
        if (eClass == null) {
            return null;
        }

        for (BusinessExceptionEnum exceptionEnum : BusinessExceptionEnum.values()) {
            if (eClass.equals(exceptionEnum.eClass)) {
                return exceptionEnum;
            }
        }
        return null;
    }
}
