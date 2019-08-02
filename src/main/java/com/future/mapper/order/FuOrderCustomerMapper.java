package com.future.mapper.order;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.order.FuOrderCustomer;
import org.springframework.stereotype.Repository;


@Repository
public interface FuOrderCustomerMapper extends BaseMapper<FuOrderCustomer> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuOrderCustomer record);

    int insertSelective(FuOrderCustomer record);

    FuOrderCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuOrderCustomer record);

    int updateByPrimaryKey(FuOrderCustomer record);

    FuOrderCustomer getLastCustomerOrder(String username);
}