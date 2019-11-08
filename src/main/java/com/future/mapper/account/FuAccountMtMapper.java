package com.future.mapper.account;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.account.FuAccountMt;
import com.future.pojo.bo.order.UserMTAccountBO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FuAccountMtMapper extends BaseMapper<FuAccountMt> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuAccountMt record);

    int insertSelective(FuAccountMt record);

    FuAccountMt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuAccountMt record);

    int updateByPrimaryKey(FuAccountMt record);

    List<UserMTAccountBO> selectUserMTAccByCondition(Map conditionMap);
}