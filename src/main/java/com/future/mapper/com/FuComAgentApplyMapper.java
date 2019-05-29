package com.future.mapper.com;

import com.future.entity.com.FuComAgentApply;

public interface FuComAgentApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuComAgentApply record);

    int insertSelective(FuComAgentApply record);

    FuComAgentApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComAgentApply record);

    int updateByPrimaryKey(FuComAgentApply record);
}