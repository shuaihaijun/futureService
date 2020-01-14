package com.future.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.ResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.result.DefaultErrorResult;
import com.future.common.result.Page;
import com.future.common.result.PlatformResult;
import com.github.pagehelper.PageInfo;

/**
 * 统一响应格式工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class ResultUtil {

    private static String DEFAULT_KEY = "data";

    /**
     * 请求失败返回结果，无业务数据
     *
     * @param resultCode
     * @return
     */
    public static DefaultErrorResult error(GlobalResultCode resultCode) {
        DefaultErrorResult result = new DefaultErrorResult();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setMsg(resultCode.message());
        return result;
    }

    /**
     * 请求失败返回结果，无业务数据
     *
     * @param business
     * @return
     */
    public static DefaultErrorResult error(BusinessException business) {
        DefaultErrorResult result = new DefaultErrorResult();
        ResultCode resultCode = business.getResultCode();
        String message = StringUtils.isEmpty(business.getMessage()) ? resultCode.message() : business.getMessage();
        result.setCode(resultCode.code());
        result.setMessage(message);
        result.setMsg(message);
        return result;
    }

    /**
     * 请求失败返回结果，包含请求失败业务数据
     *
     * @param resultCode
     * @param data
     * @return
     */
    public static DefaultErrorResult error(GlobalResultCode resultCode, Object data) {
        DefaultErrorResult result = new DefaultErrorResult();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setData(data);
        result.setMsg(resultCode.message());
        result.setContent(data);
        return result;
    }

    /**
     * 请求失败返回结果，自定义返回状态码与提示信息
     *
     * @param code
     * @param message
     * @return
     */
    public static DefaultErrorResult error(Integer code, String message) {
        DefaultErrorResult result = new DefaultErrorResult();
        result.setCode(code);
        result.setMessage(message);
        result.setMsg(message);
        return result;
    }

    public static Map<String, Object> handlePageInfo(PageInfo<?> pageInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        Page page = new Page();
        page.setPageNo(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getSize());
        page.setTotal(pageInfo.getTotal());
        page.setTotalPages(pageInfo.getPages());
        page.setSize(pageInfo.getSize());

        // 封装分页信息和结果集
        map.put("page", page);
        map.put("pageList", pageInfo.getList());

        return map;
    }

    /**
     * 请求成功返回结果，包含请求的业务数据
     *
     * @param data
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> PlatformResult<T> success(T data) {
        PlatformResult<T> result = new PlatformResult<>();
        GlobalResultCode success = GlobalResultCode.SUCCESS;
        result.setStatus(success.code());
        result.setMsg(success.message());
        Map<String, Object> resultMap = new HashMap<>();
        if (data instanceof List || data instanceof String || data instanceof Integer || data instanceof Long) {
            resultMap.put(DEFAULT_KEY, data);
            result.setContent((T) resultMap);
        } else {
            result.setContent(data);
        }
        return result;
    }

    public static <T> PlatformResult<T> success(PageInfo<T> pageInfo) {
        Page page = new Page();
        page.setPageNo(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getSize());
        page.setTotal(pageInfo.getTotal());
        page.setTotalPages(pageInfo.getPages());
        page.setSize(pageInfo.getSize());

        PlatformResult<T> result = new PlatformResult<>();
        GlobalResultCode success = GlobalResultCode.SUCCESS;
        result.setStatus(success.code());
        result.setMsg(success.message());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(DEFAULT_KEY, pageInfo.getList());
        result.setContent((T) resultMap);
        result.setPage(page);
        return result;
    }

    public static <T> PlatformResult<T> success(com.github.pagehelper.Page<T> pageInfo) {
        Page page = new Page();
        page.setPageNo(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        page.setTotalPages(pageInfo.getPages());
        page.setSize(pageInfo.getPageSize());

        PlatformResult<T> result = new PlatformResult<>();
        GlobalResultCode success = GlobalResultCode.SUCCESS;
        result.setStatus(success.code());
        result.setMsg(success.message());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(DEFAULT_KEY, pageInfo.getResult());
        result.setContent((T) resultMap);
        result.setPage(page);
        return result;
    }
}