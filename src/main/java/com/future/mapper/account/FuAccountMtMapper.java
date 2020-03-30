package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountMt;
import com.future.pojo.bo.account.UserMTAccountBO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface FuAccountMtMapper extends BaseMapper<FuAccountMt> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountMt record);

    int insertSelective(FuAccountMt record);

    FuAccountMt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountMt record);

    int updateByPrimaryKey(FuAccountMt record);

    List<UserMTAccountBO> selectUserMTAccByCondition(Map conditionMap);

    int updateByPrimaryCondition(FuAccountMt record);

    List<UserMTAccountBO> selectUserMTAccByProjectCondition(Map conditionMap);
}