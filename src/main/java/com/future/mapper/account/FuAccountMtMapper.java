package com.future.mapper.account;

import com.future.entity.account.FuAccountMt;

public interface FuAccountMtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountMt record);

    int insertSelective(FuAccountMt record);

    FuAccountMt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountMt record);

    int updateByPrimaryKey(FuAccountMt record);
}