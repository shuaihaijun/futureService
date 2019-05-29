package com.future.mapper.order;

import com.future.entity.order.FuOrderInfo;

public interface FuOrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuOrderInfo record);

    int insertSelective(FuOrderInfo record);

    FuOrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderInfo record);

    int updateByPrimaryKey(FuOrderInfo record);
}