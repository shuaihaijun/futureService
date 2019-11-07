package com.future.mapper.user;

import com.future.entity.user.FuUserFollows;

public interface FuUserFollowsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUserFollows record);

    int insertSelective(FuUserFollows record);

    FuUserFollows selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserFollows record);

    int updateByPrimaryKey(FuUserFollows record);
}