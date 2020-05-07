package com.future.service.product;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.ParameterInvalidException;
import com.future.entity.product.FuProductSignalValuation;
import com.future.mapper.product.FuProductSignalValuationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuProductSignalValuationService extends ServiceImpl<FuProductSignalValuationMapper,FuProductSignalValuation> {


    Logger log= LoggerFactory.getLogger(FuProductSignalValuationService.class);
    @Autowired
    FuProductSignalValuationMapper fuProductSignalValuationMapper;

    /**
     * 获取信号源评估信息
     * @param signalId
     * @return
     */
    public FuProductSignalValuation getSignalValuation(Integer signalId){
        if(signalId==null||signalId==0){
            log.error("获取信号源评估信息 信号源ID为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        FuProductSignalValuation valuation= selectOne(new EntityWrapper<FuProductSignalValuation>().eq(FuProductSignalValuation.SIGNAL_ID,signalId));
        if(valuation==null||valuation.getId()==null){
            return null;
        }
        return valuation;
    }

    /**
     * 插入
     * @param signalValuation
     */
    public void insertSelective(FuProductSignalValuation signalValuation){
        if(signalValuation==null||signalValuation.getSignalId()==null){
            log.error("插入信号源评估信息 信号源ID为空！");
            throw new ParameterInvalidException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        fuProductSignalValuationMapper.insertSelective(signalValuation);
    }
}
