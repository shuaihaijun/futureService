package com.future.service.permission;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.RmsResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.util.StringUtils;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.mapper.permission.FuPermissionUserRoleMapper;
import com.future.pojo.bo.permission.FuPermissionUserRoleBO;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户角色关联信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionUserRoleService extends ServiceImpl<FuPermissionUserRoleMapper, FuPermissionUserRole> {

    @Autowired
    private FuPermissionUserRoleMapper fuPermissionUserRoleMapper;

    /**
     * 通过用户ID查询所关联的角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    public List<Integer> findRoleIdsByUserId(Integer userId) {
        //验证必要参数值是否为空
        if (userId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuPermissionUserRoleMapper.selectRoleIdsByUserId(userId);
    }

    /**
     * 查询所有的用户ID集合并去重
     *
     * @return 用户ID集合
     */
    public List<Integer> findUserIds() {
        return fuPermissionUserRoleMapper.selectUserIds();
    }

    /**
     * 通过角色ID集合查询所关联的用户ID集合并去重
     *
     * @param roleIds 角色ID集合
     * @return 用户ID集合
     */
    public List<Integer> findUserIdsByRoleIds(List<Integer> roleIds) {
        return fuPermissionUserRoleMapper.selectUserIdsByRoleIds(roleIds);
    }

    /**
     * 通过用户ID以及角色ID删除用户角色关联数据
     *
     * @param fuPermissionUserRoleBO 删除条件（用户ID、角色ID）
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(FuPermissionUserRoleBO fuPermissionUserRoleBO) {
        //验证参数对象是否为空
        if (fuPermissionUserRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer userId = fuPermissionUserRoleBO.getUserId();
        Integer roleId = fuPermissionUserRoleBO.getRoleId();
        //验证必要参数值是否为空
        if (userId == null || roleId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }
        boolean isSuccess = delete(new EntityWrapper<FuPermissionUserRole>().eq(FuPermissionUserRole.USER_ID, userId).and().eq(FuPermissionUserRole.ROLE_ID, roleId));
        if (!isSuccess) {
            throw new BusinessException(RmsResultCode.PERMISSION_USER_PROJECT_DATA_SAVE_FAILURE);
        }
    }

    /**
     * 通过用户ID和角色ID集合删除用户角色关联数据
     *
     * @param fuPermissionUserRoleBO 删除条件（用户ID、角色ID集合）
     * @return 删除的条数
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeBySelective(FuPermissionUserRoleBO fuPermissionUserRoleBO) {
        //验证参数对象是否为空
        if (fuPermissionUserRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer userId = fuPermissionUserRoleBO.getUserId();
        Integer roleId = fuPermissionUserRoleBO.getRoleId();
        List<Integer> roleIds = fuPermissionUserRoleBO.getRoleIds();
        //验证必要参数值是否为空
        if (userId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }
        boolean isSuccess = delete(new EntityWrapper<FuPermissionUserRole>().eq(FuPermissionUserRole.USER_ID, userId)
                .eq(roleId != null, "Role_id", roleId)
                .in(StringUtils.isNotEmpty(roleIds), "Role_id", roleIds));
        if (!isSuccess) {
            throw new BusinessException(RmsResultCode.PERMISSION_USER_PROJECT_DATA_SAVE_FAILURE);
        }
    }

    /**
     * 新增用户角色关联信息
     *
     * @param fuPermissionUserRoleBO 保存的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(FuPermissionUserRoleBO fuPermissionUserRoleBO) {
        //验证参数对象是否为空
        if (fuPermissionUserRoleBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer userId = fuPermissionUserRoleBO.getUserId();
        List<Integer> roleIds = fuPermissionUserRoleBO.getRoleIds();
        //验证必要参数值是否为空
        if (userId == null || StringUtils.isEmpty(roleIds)) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }

        //避免重复保存已有的关系
        /*List<Integer> uRoleIds = findRoleIdsByUserId(userId);
        if (StringUtils.isNotEmpty(uRoleIds)) {
            roleIds.removeAll(uRoleIds);
        }
        if (StringUtils.isEmpty(roleIds)) {
            throw new BusinessException(RmsResultCode.PERMISSION_USER_ROLE_BIND);
        }*/

        //组装批量插入对象
        List<FuPermissionUserRole> param = Lists.newArrayList();
        for (Integer roleId : roleIds) {
            FuPermissionUserRole permissionUserRole = new FuPermissionUserRole();
            permissionUserRole.setUserId(userId);
            permissionUserRole.setRoleId(roleId);
            param.add(permissionUserRole);
        }
        /*清除该用户 原来角色*/
        delete(new EntityWrapper<FuPermissionUserRole>().eq(FuPermissionUserRole.USER_ID, userId));

        /*新增角色*/
        boolean isSuccess = insertBatch(param);
        if (!isSuccess) {
            throw new BusinessException(RmsResultCode.PERMISSION_USER_PROJECT_DATA_SAVE_FAILURE);
        }
    }


    /**
     * 查询人员角色关系
     * @param requestMap 查询人员角色关系
     */
    @Transactional(rollbackFor = Exception.class)
    public PageInfo<FuPermissionUserRoleBO> queryUserRole(JSONObject requestMap) {
        //验证参数对象是否为空
        if (requestMap == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        int pageSize=20;
        int pageNum=1;
        if(StringUtils.isEmpty(pageSize)||StringUtils.isEmpty(pageNum)){
            log.warn("分页数据为空！");
        }else {
            if(requestMap.getString("pageSize")!=null){
                pageSize=Integer.parseInt(requestMap.getString("pageSize"));
            }
            if(requestMap.getString("pageNum")!=null){
                pageNum=Integer.parseInt(requestMap.getString("pageNum"));
            }
        }

        Map conditionMap =new HashMap();
        if(!ObjectUtils.isEmpty(requestMap.getString("userId"))){
            conditionMap.put("userId",requestMap.getString("userId"));
        }
        if(!ObjectUtils.isEmpty(requestMap.getString("username"))){
            conditionMap.put("username",requestMap.getString("username"));
        }
        if(!ObjectUtils.isEmpty(requestMap.getString("roleId"))){
            conditionMap.put("roleId",requestMap.getString("roleId"));
        }
        if(!ObjectUtils.isEmpty(requestMap.getString("roleName"))){
            conditionMap.put("roleName",requestMap.getString("roleName"));
        }
        if(!ObjectUtils.isEmpty(requestMap.getString("refName"))){
            conditionMap.put("refName",requestMap.getString("refName"));
        }
        List<FuPermissionUserRoleBO> userRole = fuPermissionUserRoleMapper.queryUserRole(conditionMap);

        return new PageInfo(userRole);
    }

}