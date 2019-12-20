package com.future.mapper.commission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.commission.FuCommissionCustomer;
import org.springframework.stereotype.Component;

@Component
public interface FuCommissionCustomerMapper extends BaseMapper<FuCommissionCustomer> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuCommissionCustomer record);

    int insertSelective(FuCommissionCustomer record);

    FuCommissionCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuCommissionCustomer record);

    int updateByPrimaryKey(FuCommissionCustomer record);
}