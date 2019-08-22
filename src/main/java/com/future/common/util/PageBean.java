package com.future.common.util;


import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * 自定义分页bean
 *
 * @author Admin
 * @version: 1.0
 */
public class PageBean<T> implements Serializable {

    Logger log= LoggerFactory.getLogger(PageBean.class);

    Page<T> page=new Page<T>();
    int pageSize=20;
    int pageNum=1;

    public Page<T> getPage(Map pageMap) {

        page.setSize(pageSize);
        page.setCurrent(pageNum);
        if (!StringUtils.isEmpty(String.valueOf(pageMap.get("pageSize")))) {
            pageSize = Integer.parseInt(String.valueOf(pageMap.get("pageSize")));
        }
        if (!StringUtils.isEmpty(String.valueOf(pageMap.get("pageNum")))) {
            pageNum = Integer.parseInt(String.valueOf(pageMap.get("pageNum")));
        }
        return page;
    }
}