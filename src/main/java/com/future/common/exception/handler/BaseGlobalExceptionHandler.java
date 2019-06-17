package com.future.common.exception.handler;


import java.net.BindException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.helper.ParameterInvalidItem;
import com.future.common.helper.ParameterInvalidItemHelper;
import com.future.common.result.DefaultErrorResult;
import com.future.common.util.LogUtil;
import com.future.common.util.ResultUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;


/**
 * 全局通用异常处理基础类
 *
 * @author Admin
 * @version: 1.0
 */
public class BaseGlobalExceptionHandler {

    protected LogUtil logger = LogUtil.logger(GlobalExceptionHandler.class);
    /**
     * 异常处理方法
     * @param e 具体异常参数
     * @param request 记录日志是使用，获取请求的URI
     * @return
     */
//	public DefaultErrorResult handle(Exception e, HttpServletRequest request){

//        if(e instanceof ConstraintViolationException){
//        	/**
//    		 * 违反数据库主键约束异常，例如主键长度约束、字符约束等
//    		 */
//            return ResultUtil.error(ResultCode.PARAM_CONSTRAINT_VIOLATION);
//        }else if(e instanceof NullPointerException) {
//        	/**
//        	 * 空指针异常
//        	 */
//        	return ResultUtil.error(ResultCode.SYSTEM_NULL_POINTER);
//        }else if(e instanceof ClassNotFoundException){
//        	/**
//    		 * 指定的类不存在
//    		 */
//        	return ResultUtil.error(ResultCode.SYSTEM_CLASS_NOTFOUND);
//        }else if(e instanceof ArithmeticException) {
//        	/**
//        	 * 数学运算异常
//        	 */
//        	return ResultUtil.error(ResultCode.SYSTEM_ARITHMETIC);
//        }else if(e instanceof ArrayIndexOutOfBoundsException) {
//        	/**
//        	 * 数组下标越界
//        	 */
//        	return ResultUtil.error(ResultCode.SYSTEM_INDEX_OUTOFBOUNDS);
//        }else if(e instanceof IllegalArgumentException) {
//        	/**
//        	 * 方法的参数错误
//        	 */
//        	return ResultUtil.error(ResultCode.SYSTEM_ILLEGAL_ARGUMENTS);
//        }else if(e instanceof IllegalAccessException) {
//        	/**
//        	 * 没有访问权限
//        	 */
//        	return ResultUtil.error(ResultCode.SYSTEM_ILLEGA_ACCESS);
//        }else if(e instanceof Exception){
//        	/**
//        	 * 处理未定义异常
//        	 */
//            return ResultUtil.error(ResultCode.SYSTEM_INNER_ERROR);
//        }else{
//			DefaultErrorResult defaultErrorResult = ResultUtil.error(e);
//			return ResponseEntity
//					.status(HttpStatus.valueOf(defaultErrorResult.getStatus()))
//					.body(defaultErrorResult);
//		}
//    }

    /**
     * 违反约束异常
     */
    protected DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        logger.error("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertCVSetToParameterInvalidItemList(e.getConstraintViolations());
        return ResultUtil.error(GlobalResultCode.PARAM_IS_INVALID, parameterInvalidItemList);
    }

    /**
     * 处理验证参数封装错误时异常
     */
    protected DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.error("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return ResultUtil.error(GlobalResultCode.PARAM_IS_INVALID);
    }

    /**
     * 处理参数绑定时异常（反400错误码）
     */
    protected DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
        logger.error("handleBindException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return ResultUtil.error(GlobalResultCode.PARAM_IS_INVALID);
    }

    /**
     * 处理使用@Validated注解时，参数验证错误异常（反400错误码）
     */
    protected DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.error("handleMethodArgumentNotValidException start, uri:{}, caused by: ", request.getRequestURI(), e);
        List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        return ResultUtil.error(GlobalResultCode.PARAM_IS_INVALID, parameterInvalidItemList);
    }

    /**
     * 处理通用自定义业务异常
     */
    protected DefaultErrorResult handleBusinessException(BusinessException e, HttpServletRequest request) {
        logger.error(e);
        return ResultUtil.error(e);
    }

    /**
     * 处理运行时系统异常（反500错误码）
     */
    protected DefaultErrorResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        logger.error("handleRuntimeException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return ResultUtil.error(GlobalResultCode.SYSTEM_INNER_ERROR);
    }
}
