package com.future.common.helper;

import lombok.Data;

/**
 * 分页辅助类，用于controller层接受client端分页参数
 *
 * @author Admin
 * @version: 1.0
 */
@Data
public class PageInfoHelper {
    /**
     * 每页条数
     */
    private Integer pageSize = 20;
    /**
     * 分页页码
     */
    private Integer pageNo = 1;

    public PageInfoHelper() {
    }

    public PageInfoHelper(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

}