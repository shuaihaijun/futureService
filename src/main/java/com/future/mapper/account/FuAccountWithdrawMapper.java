package com.future.mapper.account;

import com.future.entity.account.FuAccountWithdraw;

public interface FuAccountWithdrawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuAccountWithdraw record);

    int insertSelective(FuAccountWithdraw record);

    FuAccountWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountWithdraw record);

    int updateByPrimaryKey(FuAccountWithdraw record);
}