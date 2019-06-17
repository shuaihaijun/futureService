package com.future.common.exception.handler;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.result.DefaultErrorResult;
import com.future.common.util.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



/**
 * 统一异常处理拦截器
 *
 * @author Admin
 * @version: 1.0
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {

    /**
     * 违反数据库主键约束异常，例如主键长度约束、字符约束等
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        return super.handleConstraintViolationException(e, request);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return super.handleMethodArgumentNotValidException(e, request);
    }

    /**
     * 空指针异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public DefaultErrorResult handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.PARAM_NULL_POINTER);
    }

    /**
     * 指定的类不存在
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClassNotFoundException.class)
    public DefaultErrorResult handleClassNotFoundException(ClassNotFoundException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.SYSTEM_CLASS_NOTFOUND);
    }

    /**
     * 数学运算异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArithmeticException.class)
    public DefaultErrorResult handleArithmeticException(ArithmeticException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.SYSTEM_ARITHMETIC);
    }

    /**
     * 数组下标越界
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public DefaultErrorResult handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.SYSTEM_INDEX_OUTOFBOUNDS);
    }

    /**
     * 方法的参数错误
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public DefaultErrorResult handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.PARAM_IS_INVALID);
    }

    /**
     * 没有访问权限
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalAccessException.class)
    public DefaultErrorResult handleIllegalAccessException(IllegalAccessException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.SYSTEM_ILLEGA_ACCESS);
    }

    /**
     * 统一处理400
     *
     * @param request
     * @param exception
     * @return 自定义返回对象
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public DefaultErrorResult excHandler(HttpServletRequest request, Exception exception) {
        logger.error(exception);
        return ResultUtil.error(GlobalResultCode.PARAM_NOT_COMPLETE, exception.getMessage());
    }

    /**
     * 业务异常处理
     */
    @Override
    @ExceptionHandler(BusinessException.class)
    public DefaultErrorResult handleBusinessException(BusinessException e, HttpServletRequest request) {
        logger.error(e);
        return super.handleBusinessException(e, request);
    }

    /**
     * 未知异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public DefaultErrorResult handleException(Exception e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(GlobalResultCode.SYSTEM_INNER_ERROR);
    }

}