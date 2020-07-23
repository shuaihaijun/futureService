package com.future.mapper.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.user.FuUserFollows;
import com.future.pojo.vo.signal.FuFollowUserVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface FuUserFollowsMapper extends BaseMapper<FuUserFollows> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuUserFollows record);

    int insertSelective(FuUserFollows record);

    FuUserFollows selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuUserFollows record);

    int updateByPrimaryKey(FuUserFollows record);

    List<FuFollowUserVO> queryFollowUsers(Map conditionMap);

    List<FuFollowUserVO> queryProjectFollowUsers(Map conditionMap);

    List<FuUserFollows> queryProjectSignalFollows(Map conditionMap);
}