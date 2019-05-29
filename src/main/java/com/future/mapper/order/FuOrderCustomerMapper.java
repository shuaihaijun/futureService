package com.future.mapper.order;

import com.future.entity.order.FuOrderCustomer;

public interface FuOrderCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuOrderCustomer record);

    int insertSelective(FuOrderCustomer record);

    FuOrderCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderCustomer record);

    int updateByPrimaryKey(FuOrderCustomer record);
}