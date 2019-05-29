package com.future.mapper.pay;

import com.future.entity.pay.FuPayOrder;

public interface FuPayOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuPayOrder record);

    int insertSelective(FuPayOrder record);

    FuPayOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuPayOrder record);

    int updateByPrimaryKey(FuPayOrder record);
}