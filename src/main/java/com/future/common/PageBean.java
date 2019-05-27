package com.future.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * 分页组件
 */
public class PageBean implements Serializable {

	/** 序列化ID */
	private static final long serialVersionUID = -1054411607230549314L;

	// 指定的或是页面参数
	private int currentPage; // 当前页
	private int numPerPage; // 每页显示多少条

	// 查询数据库
	private int totalCount; // 总记录数
	private List dataList; // 本页的数据列表

	// 计算
	private int pageCount; // 总页数
	private int beginPageIndex; // 页码列表的开始索引（包含）
	private int endPageIndex; // 页码列表的结束索引（包含）

	private Map<String, Object> countResultMap; // 当前分页条件下的统计结果

	public PageBean() {
	}

	/**
	 * @description
	 * @author hely
	 * @date 2018/4/20
	 * @param dataList
	 * @param currentPage
	 * @param numPerPage
	 * @param totalCount
	 */
	public PageBean(List dataList,int currentPage, int numPerPage, int totalCount) {

		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.dataList = dataList;
		// 计算总页码
		pageCount = totalCount % numPerPage == 0 ? totalCount / numPerPage : (totalCount / numPerPage) + 1;

	}

	/**
	 * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param totalCount
	 * @param dataList
	 */
	public PageBean(int currentPage, int numPerPage, int totalCount, List dataList) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.dataList = dataList;

		// 计算总页码
		pageCount = (totalCount + numPerPage - 1) / numPerPage;

		// 计算 beginPageIndex 和 endPageIndex
		// >> 总页数不多于10页，则全部显示
		if (pageCount <= 10) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;
			// 当前面的页码不足4个时，则显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面的页码不足5个时，则显示后10个页码
			if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 10 + 1;
			}
		}
	}

	/**
	 * 只接受前5个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param totalCount
	 * @param dataList
	 */
	public PageBean(int currentPage, int numPerPage, int totalCount,
			List dataList, Map<String, Object> countResultMap) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.dataList = dataList;
		this.countResultMap = countResultMap;

		// 计算总页码
		pageCount = (totalCount + numPerPage - 1) / numPerPage;

		// 计算 beginPageIndex 和 endPageIndex
		// >> 总页数不多于10页，则全部显示
		if (pageCount <= 10) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;
			// 当前面的页码不足4个时，则显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面的页码不足5个时，则显示后10个页码
			if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 10 + 1;
			}
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public Map<String, Object> getCountResultMap() {
		return countResultMap;
	}

	public void setCountResultMap(Map<String, Object> countResultMap) {
		this.countResultMap = countResultMap;
	}


	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
}
