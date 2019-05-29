package com.future.mapper.commission;

import com.future.entity.commission.FuCommissionInfo;

public interface FuCommissionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuCommissionInfo record);

    int insertSelective(FuCommissionInfo record);

    FuCommissionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuCommissionInfo record);

    int updateByPrimaryKey(FuCommissionInfo record);
}