package com.future.mapper.user;

import com.future.entity.user.FuUserBank;

public interface FuUserBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUserBank record);

    int insertSelective(FuUserBank record);

    FuUserBank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserBank record);

    int updateByPrimaryKey(FuUserBank record);
}