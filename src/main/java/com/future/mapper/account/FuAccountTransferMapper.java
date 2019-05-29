package com.future.mapper.account;

import com.future.entity.account.FuAccountTransfer;

public interface FuAccountTransferMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountTransfer record);

    int insertSelective(FuAccountTransfer record);

    FuAccountTransfer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountTransfer record);

    int updateByPrimaryKey(FuAccountTransfer record);
}