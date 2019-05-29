package com.future.mapper.commission;

import com.future.entity.commission.FuCommissionLevel;

public interface FuCommissionLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuCommissionLevel record);

    int insertSelective(FuCommissionLevel record);

    FuCommissionLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuCommissionLevel record);

    int updateByPrimaryKey(FuCommissionLevel record);
}