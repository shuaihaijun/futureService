package com.future.mapper.commission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.commission.FuCommissionLevel;

public interface FuCommissionLevelMapper extends BaseMapper<FuCommissionLevel> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuCommissionLevel record);

    int insertSelective(FuCommissionLevel record);

    FuCommissionLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuCommissionLevel record);

    int updateByPrimaryKey(FuCommissionLevel record);
}