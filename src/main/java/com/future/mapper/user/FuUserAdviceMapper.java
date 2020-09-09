package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUserAdvice;
import org.springframework.stereotype.Component;

@Component
public interface FuUserAdviceMapper extends BaseMapper<FuUserAdvice> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUserAdvice record);

    int insertSelective(FuUserAdvice record);

    FuUserAdvice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserAdvice record);

    int updateByPrimaryKey(FuUserAdvice record);
}