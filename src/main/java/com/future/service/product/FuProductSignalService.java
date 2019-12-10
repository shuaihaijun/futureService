package com.future.service.product;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalApply;
import com.future.mapper.product.FuProductSignalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class FuProductSignalService extends ServiceImpl<FuProductSignalMapper,FuProductSignal> {

    Logger log= LoggerFactory.getLogger(FuProductSignalService.class);

    /**
     * 根据条件查找信号源信息
     * @param conditionMap
     * @return
     */
    public Page<FuProductSignal> findSignalByCondition(Map conditionMap){
        /*校验信息*/
        Page<FuProductSignal> page=new Page<FuProductSignal>();
        Wrapper<FuProductSignal> wrapper=new EntityWrapper<FuProductSignal>();

        int pageSize=20;
        int pageNum=1;

        if(StringUtils.isEmpty(pageSize)||StringUtils.isEmpty(pageNum)){
            log.error("分页数据为空！");
            new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER,"分页数据为空！");
        }
        if(!StringUtils.isEmpty(String.valueOf(conditionMap.get("pageSize")))){
            pageSize=Integer.parseInt(String.valueOf(conditionMap.get("pageSize")));
        }
        if(!StringUtils.isEmpty(String.valueOf(conditionMap.get("pageNum")))){
            pageNum=Integer.parseInt(String.valueOf(conditionMap.get("pageNum")));
        }
        page.setSize(pageSize);
        page.setCurrent(pageNum);

        if(!ObjectUtils.isEmpty(conditionMap.get("signalId"))){
            wrapper.eq(FuProductSignal.APPLY_ID,String.valueOf(conditionMap.get("signalId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("userId"))){
            wrapper.eq(FuProductSignal.USER_ID,String.valueOf(conditionMap.get("userId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("signalName"))){
            wrapper.eq(FuProductSignal.SIGNAL_NAME,String.valueOf(conditionMap.get("signalName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("signalState"))){
            if(String.valueOf(conditionMap.get("signalState")).indexOf(",")<0){
                wrapper.eq(FuProductSignal.SIGNAL_STATE,String.valueOf(conditionMap.get("signalState")));
            }else {
                //多状态
                String[] state=String.valueOf(conditionMap.get("signalState")).split(",");
                Wrapper<FuProductSignal> stateWrapper=new EntityWrapper<FuProductSignal>();
                wrapper.andNew().eq(FuProductSignal.SIGNAL_STATE,state[0]);
                for (int i=1;i<state.length;i++){
                    wrapper.or().eq(FuProductSignal.SIGNAL_STATE,state[i]);
                }
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("serverName"))){
            wrapper.eq(FuProductSignal.SERVER_NAME,String.valueOf(conditionMap.get("serverName")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("mtAccId"))){
            wrapper.eq(FuProductSignal.MT_ACC_ID,String.valueOf(conditionMap.get("mtAccId")));
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("createDate"))){
            if(String.valueOf(conditionMap.get("createDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignal.CREATE_DATE,conditionMap.get("createDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("createDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("orderCloseDate"));
                }
                wrapper.gt(FuProductSignal.CREATE_DATE,dateClose[0]);
                wrapper.lt(FuProductSignal.CREATE_DATE,dateClose[1]);
            }
        }
        if(!ObjectUtils.isEmpty(conditionMap.get("checkDate"))){
            if(String.valueOf(conditionMap.get("checkDate")).indexOf(",")<0){
                wrapper.eq(FuProductSignal.CHECK_DATE,conditionMap.get("checkDate"));
            }else {
                //时间段
                String[] dateClose=String.valueOf(conditionMap.get("checkDate")).split(",");
                if(dateClose.length!=2){
                    log.error("创建时间段数据传入错误！"+conditionMap.get("checkDate"));
                    throw new DataConflictException(GlobalResultCode.PARAM_VERIFY_ERROR,"创建时间段数据传入错误！"+conditionMap.get("checkDate"));
                }
                wrapper.gt(FuProductSignal.CHECK_DATE,dateClose[0]);
                wrapper.lt(FuProductSignal.CHECK_DATE,dateClose[1]);
            }
        }
        return selectPage(page,wrapper);
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
