package com.future.service.product;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.entity.product.FuProductSignal;
import com.future.mapper.product.FuProductSignalMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FuProductSignalService extends ServiceImpl<FuProductSignalMapper,FuProductSignal> {


    /**
     * 根据条件查找信号源信息
     * @param conditionMap
     * @return
     */
    public List<FuProductSignal> findSignalByCondition(Map conditionMap){
        /*校验信息*/
        return selectByMap(conditionMap);
    }

    /**
     * 根据ID获取信号源信息
     * @param signalId
     * @return
     */
    public FuProductSignal findSignalById(int signalId){
        /*校验信息*/
        return selectById(signalId);
    }


    /**
     * 保存信号源信息
     * @param signalMap
     */
    public Boolean saveProductSignal(Map signalMap){
        /*校验信息*/
        FuProductSignal signal=new FuProductSignal();
        /*组装信息*/
        return insertOrUpdate(signal);
    }

    /**
     * 修改信号源信息
     * @param signalMap
     */
    public Boolean updateProductSignal(int signalId, Map signalMap){
        /*校验信息*/
        /*组装信息*/
        FuProductSignal signal=new FuProductSignal();
        return updateById(signal);
    }

    /**
     * 删除信号源信息
     * @param signalId
     */
    public Boolean deleteProductSignal(int signalId){
        /**校验信息*/
        return deleteById(signalId);
    }
}
