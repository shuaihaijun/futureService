package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUserBank;
import org.springframework.stereotype.Component;

@Component
public interface FuUserBankMapper extends BaseMapper<FuUserBank> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUserBank record);

    int insertSelective(FuUserBank record);

    FuUserBank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserBank record);

    int updateByPrimaryKey(FuUserBank record);
}