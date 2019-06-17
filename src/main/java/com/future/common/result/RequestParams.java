package com.future.common.result;

import com.future.common.helper.PageInfoHelper;
import lombok.Data;

/**
 * 请求公共类
 *
 * @author Admin
 * @version: 1.0
 */
@Data
public class RequestParams<T> {
    /**
     * 请求来源
     */
    private String source;
    /**
     * client
     */
    private String version;
    /**
     * 登录token
     */
    private String admintoken;
    /**
     * 业务参数
     */
    private T params;
    /**
     * 分页信息
     */
    private PageInfoHelper pageInfoHelper;

}
