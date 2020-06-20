package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountMtFlow;
import org.springframework.stereotype.Repository;

@Repository
public interface FuAccountMtFlowMapper extends BaseMapper<FuAccountMtFlow> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountMtFlow record);

    int insertSelective(FuAccountMtFlow record);

    FuAccountMtFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountMtFlow record);

    int updateByPrimaryKey(FuAccountMtFlow record);
}