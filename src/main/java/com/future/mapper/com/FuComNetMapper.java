package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComNet;
import org.springframework.stereotype.Component;

@Component
public interface FuComNetMapper extends BaseMapper<FuComNet> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComNet record);

    int insertSelective(FuComNet record);

    FuComNet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComNet record);

    int updateByPrimaryKey(FuComNet record);
}