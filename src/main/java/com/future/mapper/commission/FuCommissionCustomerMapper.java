package com.future.mapper.commission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountCommissionFlow;
import com.future.entity.commission.FuCommissionCustomer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface FuCommissionCustomerMapper extends BaseMapper<FuCommissionCustomer> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuCommissionCustomer record);

    int insertSelective(FuCommissionCustomer record);

    FuCommissionCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuCommissionCustomer record);

    int updateByPrimaryKey(FuCommissionCustomer record);

    List<FuAccountCommissionFlow> selectOrderCustomerSumFlow(Map conditionMap);
}