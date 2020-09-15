package com.future.service.permission;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.exception.DataConflictException;
import com.future.common.helper.PageInfoHelper;
import com.future.entity.permission.FuPermissionControl;
import com.future.mapper.permission.FuPermissionControlMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 工程项目控制模块业务实现类
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionControlService extends ServiceImpl<FuPermissionControlMapper, FuPermissionControl> {

    @Autowired
    FuPermissionControlMapper fuPermissionControlMapper;

    /**
     * 根据条件查询 团队控制信息
     * @param control
     * @return
     */
    public FuPermissionControl findByConditon(Map control){
        if(control==null){
            log.error("根据条件查询 团队控制信息失败，参数为空！");
            throw new DataConflictException("根据条件查询 团队控制信息失败，参数为空！");
        }
        Wrapper<FuPermissionControl> wrapper=new EntityWrapper();
        if(control.get("projKey")!=null && String.valueOf(control.get("projKey"))!=""){
            wrapper.eq(FuPermissionControl.PROJ_KEY,String.valueOf(control.get("projKey")));
        }
        if(control.get("controlSource")!=null && String.valueOf(control.get("controlSource"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_SOURCE,String.valueOf(control.get("controlSource")));
        }
        if(control.get("controlType")!=null && String.valueOf(control.get("controlType"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_TYPE,String.valueOf(control.get("controlType")));
        }
        if(control.get("controlPoint")!=null && String.valueOf(control.get("controlPoint"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_POINT,String.valueOf(control.get("controlPoint")));
        }
        if(control.get("controlAction")!=null && String.valueOf(control.get("controlAction"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_ACTION,String.valueOf(control.get("controlAction")));
        }
        if(control.get("controlState")!=null && String.valueOf(control.get("controlState"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_STATE,String.valueOf(control.get("controlState")));
        }
        List<FuPermissionControl> results= fuPermissionControlMapper.selectList(wrapper);
        if(results!=null&&results.size()>0){
            return results.get(0);
        }else {
            return null;
        }
    }

    /**
     * 根据条件查询 团队控制信息
     * @param control
     * @param helper
     * @return
     */
    public Page<FuPermissionControl> findPageByConditon(Map control, PageInfoHelper helper){
        if(control==null){
            log.error("根据条件查询 团队控制信息失败，参数为空！");
            throw new DataConflictException("根据条件查询 团队控制信息失败，参数为空！");
        }
        Wrapper<FuPermissionControl> wrapper=new EntityWrapper();
        if(control.get("projKey")!=null && String.valueOf(control.get("projKey"))!=""){
            wrapper.eq(FuPermissionControl.PROJ_KEY,String.valueOf(control.get("projKey")));
        }
        if(control.get("controlSource")!=null && String.valueOf(control.get("controlSource"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_SOURCE,String.valueOf(control.get("controlSource")));
        }
        if(control.get("controlType")!=null && String.valueOf(control.get("controlType"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_TYPE,String.valueOf(control.get("controlType")));
        }
        if(control.get("controlPoint")!=null && String.valueOf(control.get("controlPoint"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_POINT,String.valueOf(control.get("controlPoint")));
        }
        if(control.get("controlAction")!=null && String.valueOf(control.get("controlAction"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_ACTION,String.valueOf(control.get("controlAction")));
        }
        if(control.get("controlState")!=null && String.valueOf(control.get("controlState"))!=""){
            wrapper.eq(FuPermissionControl.CONTROL_STATE,String.valueOf(control.get("controlState")));
        }
        if (helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuPermissionControl> results= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuPermissionControlMapper.selectList(wrapper);
        return results;
    }


}