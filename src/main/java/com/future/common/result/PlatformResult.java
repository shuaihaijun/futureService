package com.future.common.result;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlatformResult<T> implements Result{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码
	 */
	private Integer status;
	/**
	 * 描述信息
	 */
	private String msg;
	/**
	 * 业务数据
	 */
	private T content;
	/**
	 * 分页信息
	 */
	private Page page;
	/**
	 * 请求时间戳
	 */
	private Long req_time;

	/**
	 * 响应时间戳
	 */
	private Long res_time;

}
