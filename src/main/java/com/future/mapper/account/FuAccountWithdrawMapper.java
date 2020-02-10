package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountWithdraw;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface FuAccountWithdrawMapper extends BaseMapper<FuAccountWithdraw> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountWithdraw record);

    int insertSelective(FuAccountWithdraw record);

    FuAccountWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountWithdraw record);

    int updateByPrimaryKey(FuAccountWithdraw record);

    List<FuAccountWithdraw> findWithdrawByProjCondtion(Map condition);
}