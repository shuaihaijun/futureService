package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUserFollows;
import org.springframework.stereotype.Component;

@Component
public interface FuUserFollowsMapper extends BaseMapper<FuUserFollows> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUserFollows record);

    int insertSelective(FuUserFollows record);

    FuUserFollows selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserFollows record);

    int updateByPrimaryKey(FuUserFollows record);
}