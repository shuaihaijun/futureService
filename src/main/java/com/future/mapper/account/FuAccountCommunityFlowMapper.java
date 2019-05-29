package com.future.mapper.account;

import com.future.entity.account.FuAccountCommunityFlow;

public interface FuAccountCommunityFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountCommunityFlow record);

    int insertSelective(FuAccountCommunityFlow record);

    FuAccountCommunityFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountCommunityFlow record);

    int updateByPrimaryKey(FuAccountCommunityFlow record);
}