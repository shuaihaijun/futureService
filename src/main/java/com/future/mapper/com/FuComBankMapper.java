package com.future.mapper.com;

import com.future.entity.com.FuComBank;

public interface FuComBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComBank record);

    int insertSelective(FuComBank record);

    FuComBank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComBank record);

    int updateByPrimaryKey(FuComBank record);
}