package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountCommission;
import org.springframework.stereotype.Component;

@Component
public interface FuAccountCommissionMapper extends BaseMapper<FuAccountCommission> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountCommission record);

    int insertSelective(FuAccountCommission record);

    FuAccountCommission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountCommission record);

    int updateByPrimaryKey(FuAccountCommission record);
}