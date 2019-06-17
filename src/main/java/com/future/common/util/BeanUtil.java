package com.future.common.util;

import com.future.common.exception.ParameterInvalidException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 实体bean转换工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class BeanUtil {

    /**
     * 拷贝实体，source,target不允许为空
     * 注意copy的属性数量越多消耗时间越长，效率非常低，谨慎使用
     *
     * @param source 源实体对象
     * @param target 目标实体对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 拷贝实体集合
     * 效率非常低，谨慎使用
     * 应用场景：DTOs <=> DOs 等
     *
     * @param sourceList  源实体对象数组
     * @param targetList  目标体对象数组
     * @param targetClazz 目标实体类类型
     */
    public static void copyPropertiesList(List sourceList, List targetList, Class targetClazz) throws InstantiationException, IllegalAccessException {
        if (CollectionUtils.isEmpty(sourceList)) {
            throw new ParameterInvalidException();
        }
        for (Object items : sourceList) {
            Object target = targetClazz.newInstance();
            BeanUtils.copyProperties(items, target);
            targetList.add(target);
        }
    }
}