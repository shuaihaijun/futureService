package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUserIdentity;
import org.springframework.stereotype.Component;

@Component
public interface FuUserIdentityMapper extends BaseMapper<FuUserIdentity> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUserIdentity record);

    int insertSelective(FuUserIdentity record);

    FuUserIdentity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserIdentity record);

    int updateByPrimaryKey(FuUserIdentity record);
}