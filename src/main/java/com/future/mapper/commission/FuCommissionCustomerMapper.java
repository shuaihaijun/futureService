package com.future.mapper.commission;

import com.future.entity.commission.FuCommissionCustomer;

public interface FuCommissionCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuCommissionCustomer record);

    int insertSelective(FuCommissionCustomer record);

    FuCommissionCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuCommissionCustomer record);

    int updateByPrimaryKey(FuCommissionCustomer record);
}