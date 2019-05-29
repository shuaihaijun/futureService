package com.future.mapper.account;

import com.future.entity.account.FuAccountCommissionFlow;

public interface FuAccountCommissionFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountCommissionFlow record);

    int insertSelective(FuAccountCommissionFlow record);

    FuAccountCommissionFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountCommissionFlow record);

    int updateByPrimaryKey(FuAccountCommissionFlow record);
}