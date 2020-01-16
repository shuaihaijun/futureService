package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FuUserMapper extends BaseMapper<FuUser> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUser record);

    int insertSelective(FuUser record);

    FuUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUser record);

    int updateByPrimaryKey(FuUser record);

    FuUser selectByUsername(String name);

    FuUser findUserIntroducer(Integer id);

    List<FuUser> queryUserListByCondition(Map conditionMap);
}