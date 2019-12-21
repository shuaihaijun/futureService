package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComAgent;
import org.springframework.stereotype.Component;

@Component
public interface FuComAgentMapper extends BaseMapper<FuComAgent> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComAgent record);

    int insertSelective(FuComAgent record);

    FuComAgent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComAgent record);

    int updateByPrimaryKey(FuComAgent record);
}