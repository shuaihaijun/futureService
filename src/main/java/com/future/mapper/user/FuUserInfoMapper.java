package com.future.mapper.user;

import com.future.entity.user.FuUserInfo;

public interface FuUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUserInfo record);

    int insertSelective(FuUserInfo record);

    FuUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserInfo record);

    int updateByPrimaryKey(FuUserInfo record);
}