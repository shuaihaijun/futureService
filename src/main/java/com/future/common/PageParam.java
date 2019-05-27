package com.future.common;

import java.io.Serializable;

/**
 * 分页参数传递工具类
 */
public class PageParam implements Serializable {

	/** 序列化ID */
	private static final long serialVersionUID = -5397863303823038573L;
	private int pageNum; // 当前页数
	private int numPerPage; // 每页记录数

	public PageParam(int pageNum, int numPerPage) {
		super();
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}

	/** 当前页数 */
	public int getPageNum() {
		return pageNum;
	}

	/** 当前页数 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/** 每页记录数 */
	public int getNumPerPage() {
		return numPerPage;
	}

	/** 每页记录数 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

}
