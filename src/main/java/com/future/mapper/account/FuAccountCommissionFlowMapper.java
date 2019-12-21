package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountCommissionFlow;
import org.springframework.stereotype.Component;

@Component
public interface FuAccountCommissionFlowMapper extends BaseMapper<FuAccountCommissionFlow> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountCommissionFlow record);

    int insertSelective(FuAccountCommissionFlow record);

    FuAccountCommissionFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountCommissionFlow record);

    int updateByPrimaryKey(FuAccountCommissionFlow record);
}