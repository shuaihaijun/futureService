package com.future.mapper.account;

import com.future.entity.account.FuAccountInfo;

public interface FuAccountInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountInfo record);

    int insertSelective(FuAccountInfo record);

    FuAccountInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountInfo record);

    int updateByPrimaryKey(FuAccountInfo record);
}