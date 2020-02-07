package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountWithdrawApply;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface FuAccountWithdrawApplyMapper extends BaseMapper<FuAccountWithdrawApply> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountWithdrawApply record);

    int insertSelective(FuAccountWithdrawApply record);

    FuAccountWithdrawApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountWithdrawApply record);

    int updateByPrimaryKey(FuAccountWithdrawApply record);

    List<FuAccountWithdrawApply> findWithdrawApplyByProjCondtion(Map condition);
}