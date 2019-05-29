package com.future.mapper.pay;

import com.future.entity.pay.FuPayInfo;

public interface FuPayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuPayInfo record);

    int insertSelective(FuPayInfo record);

    FuPayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPayInfo record);

    int updateByPrimaryKey(FuPayInfo record);
}