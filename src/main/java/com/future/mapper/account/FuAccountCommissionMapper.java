package com.future.mapper.account;

import com.future.entity.account.FuAccountCommission;

public interface FuAccountCommissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountCommission record);

    int insertSelective(FuAccountCommission record);

    FuAccountCommission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountCommission record);

    int updateByPrimaryKey(FuAccountCommission record);
}