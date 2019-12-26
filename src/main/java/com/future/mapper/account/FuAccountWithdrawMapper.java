package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountWithdraw;
import org.springframework.stereotype.Component;

@Component
public interface FuAccountWithdrawMapper extends BaseMapper<FuAccountWithdraw> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountWithdraw record);

    int insertSelective(FuAccountWithdraw record);

    FuAccountWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountWithdraw record);

    int updateByPrimaryKey(FuAccountWithdraw record);
}