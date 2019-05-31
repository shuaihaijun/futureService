package com.future.mapper.user;

import com.future.entity.user.FuUserRole;

public interface FuUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUserRole record);

    int insertSelective(FuUserRole record);

    FuUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserRole record);

    int updateByPrimaryKey(FuUserRole record);
}