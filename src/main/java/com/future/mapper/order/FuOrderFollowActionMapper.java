package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderFollowAction;

public interface FuOrderFollowActionMapper  extends BaseMapper<FuOrderFollowAction> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderFollowAction record);

    int insertSelective(FuOrderFollowAction record);

    FuOrderFollowAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderFollowAction record);

    int updateByPrimaryKey(FuOrderFollowAction record);
}