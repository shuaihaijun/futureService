package com.future.service.product;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.StringUtils;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalPermit;
import com.future.mapper.product.FuProductSignalPermitMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FuProductSignalPermitService extends ServiceImpl<FuProductSignalPermitMapper, FuProductSignalPermit> {

    Logger log= LoggerFactory.getLogger(FuProductSignalPermitService.class);

    @Autowired
    FuProductSignalPermitMapper fuProductSignalPermitMapper;
    @Autowired
    FuProductSignalService fuProductSignalService;

    /**
     * 插入
     * @param permit
     */
    public void insertSelective(FuProductSignalPermit permit){
        if(permit!=null){
            fuProductSignalPermitMapper.insertSelective(permit);
        }
    }

    /**
     * 查询信号源权限信息
     * @param conditionMap
     * @param helper
     * @return
     */
    public Page<FuProductSignalPermit> querySignalPermit(Map conditionMap,PageInfoHelper helper){

        Map serchMap=new HashMap();
        if(!StringUtils.isEmpty(conditionMap.get("signalId"))){
            serchMap.put(FuProductSignalPermit.SIGNAL_ID,conditionMap.get("signalId"));
        }
        if(!StringUtils.isEmpty(conditionMap.get("projKey"))){
            serchMap.put(FuProductSignalPermit.PROJ_KEY,conditionMap.get("projKey"));
        }

        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuProductSignalPermit> userFollows= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuProductSignalPermitMapper.selectByMap(serchMap);
        return userFollows;

    }

    /**
     * 保存信号源权限
     * @param dataMap
     */
    @Transactional(rollbackFor = Exception.class)
    public void savePermit(Map dataMap){
        if(dataMap==null||dataMap.get("signalId")==null||dataMap.get("projKeys")==null){
            log.error("保存信号源权限 出入参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER.message());
        }
        String signalId=String.valueOf(dataMap.get("signalId"));
        List<Integer> projKeys=(List<Integer>)dataMap.get("projKeys");

        /*先删除原来数据*/
        Map deleteMap=new HashMap();
        deleteMap.put(FuProductSignalPermit.SIGNAL_ID,signalId);
        fuProductSignalPermitMapper.deleteByMap(deleteMap);

        /*信号源本身所属的团队 必须有权限*/
        FuProductSignal signal= fuProductSignalService.findSignalById(Integer.parseInt(signalId));
        boolean isInclude=false;
        for(Integer projKey:projKeys ){
            if(signal.getProjKey()==projKey){
                isInclude=true;
            }
            FuProductSignalPermit permit=new FuProductSignalPermit();
            permit.setProjKey(projKey);
            permit.setSignalId(Integer.parseInt(signalId));
            fuProductSignalPermitMapper.insertSelective(permit);
        }

        /*如果不包括 需要保存*/
        if(!isInclude){
            FuProductSignalPermit permit=new FuProductSignalPermit();
            permit.setProjKey(signal.getProjKey());
            permit.setSignalId(Integer.parseInt(signalId));
            fuProductSignalPermitMapper.insertSelective(permit);
        }

    }



}
