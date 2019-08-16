package com.future.mapper.user;

import com.future.entity.user.FuUser;
import org.springframework.stereotype.Repository;

@Repository
public interface FuUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FuUser record);

    int insertSelective(FuUser record);

    FuUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUser record);

    int updateByPrimaryKey(FuUser record);

    FuUser selectByUsername(String name);
}