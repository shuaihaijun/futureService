package com.future.common.result;


import java.util.Date;

//import org.springframework.boot.autoconfigure.controller.DefaultErrorAttributes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 默认全局错误返回结果
 * 备注：该返回信息是spring boot的默认异常时返回结果
 * {@link org.springframework.boot.web.servlet.error.DefaultErrorAttributes}，目前也是我们服务的默认的错误返回结果
 *
 * @author Admin
 * @version: 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultErrorResult implements Result {

    private static final long serialVersionUID = 1899083570489722793L;

    /**
     * 系统内部自定义的返回值编码，{@link com} 它是对错误更加详细的编码
     * <p>
     * 备注：spring boot默认返回异常时，该字段为null
     */
    private Integer code;

    /**
     * 异常堆栈的精简信息
     */
    private String message;

    /**
     * 异常的错误传递的数据
     */
    private Object data;

    /**
     * 调用接口路径
     */
    private String path;

    /**
     * 异常的名字
     */
    private String exception;

    /**
     * 时间戳
     */
    private Date timestamp;

    /**
     * 描述信息
     */
    private String msg;
    /**
     * 业务数据
     */
    private Object content;

    /**
     * 请求时间戳
     */
    private Long req_time;

    /**
     * 响应时间戳
     */
    private Long res_time;

}