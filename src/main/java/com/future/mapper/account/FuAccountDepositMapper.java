package com.future.mapper.account;

import com.future.entity.account.FuAccountDeposit;

public interface FuAccountDepositMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountDeposit record);

    int insertSelective(FuAccountDeposit record);

    FuAccountDeposit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountDeposit record);

    int updateByPrimaryKey(FuAccountDeposit record);
}