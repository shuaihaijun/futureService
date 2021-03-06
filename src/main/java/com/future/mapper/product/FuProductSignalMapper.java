package com.future.mapper.product;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.product.FuProductSignal;
import com.future.pojo.vo.signal.FuUserSignalVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FuProductSignalMapper extends BaseMapper<FuProductSignal> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuProductSignal record);

    int insertSelective(FuProductSignal record);

    FuProductSignal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuProductSignal record);

    int updateByPrimaryKey(FuProductSignal record);

    List<FuUserSignalVO> querySignalUsers(Map conditionMap);

    List<FuUserSignalVO> querySignalUsersPermit(Map conditionMap);

    List<FuProductSignal> querySignalAllowed(Map conditionMap);

}