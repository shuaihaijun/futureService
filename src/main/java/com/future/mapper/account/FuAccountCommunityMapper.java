package com.future.mapper.account;

import com.future.entity.account.FuAccountCommunity;

public interface FuAccountCommunityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountCommunity record);

    int insertSelective(FuAccountCommunity record);

    FuAccountCommunity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountCommunity record);

    int updateByPrimaryKey(FuAccountCommunity record);
}