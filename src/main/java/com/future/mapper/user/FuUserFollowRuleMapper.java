package com.future.mapper.user;

import com.future.entity.user.FuUserFollowRule;

public interface FuUserFollowRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUserFollowRule record);

    int insertSelective(FuUserFollowRule record);

    FuUserFollowRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserFollowRule record);

    int updateByPrimaryKey(FuUserFollowRule record);
}