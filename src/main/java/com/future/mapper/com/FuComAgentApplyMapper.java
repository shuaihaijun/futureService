package com.future.mapper.com;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.com.FuComAgentApply;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FuComAgentApplyMapper extends BaseMapper<FuComAgentApply> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuComAgentApply record);

    int insertSelective(FuComAgentApply record);

    FuComAgentApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuComAgentApply record);

    int updateByPrimaryKey(FuComAgentApply record);

    List<FuComAgentApply> queryAgentApplyByProjCondition(Map conditionMap);
}