package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionRoleResource;
import com.future.pojo.bo.permission.FuPermissionResourceBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限资源关联信息模块Mapper接口
 *
 * @author Admin
 * @version: 1.0
 */
@Repository
public interface FuPermissionRoleResourceMapper extends BaseMapper<FuPermissionRoleResource> {

    /**
     * 通过工程项目KEY集合以及角色ID删除角色权限资源关系表数据
     *
     * @param projKeys 工程项目KEY集合
     * @param roleId   角色ID
     */
    void deleteByRoleIdProjKeys(@Param("roleId") String roleId, @Param("projKeys") List<Integer> projKeys);

    /**
     * 通过角色ID查询所关联的权限资源ID拼接的字符串
     *
     * @param roleId 角色ID
     * @return 权限ID集合
     */
    List<String> selectResIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 通过角色ID查询所关联的权限资源
     * @param roleId
     * @return
     */
    List<FuPermissionResourceBO> findResByRoleId(@Param("roleId") Integer roleId);

    /**
     * 通过角色ID集合查询角色所关联的权限资源ID集合并去重
     *
     * @param roleIds 角色ID集合
     * @return 权限ID集合
     */
    List<Integer> selectResIdByRoleIds(@Param("roleIds") List<Integer> roleIds);

    /**
     * 通过权限资源ID查询所关联的角色ID集合
     *
     * @param resId 权限ID
     * @return 角色ID集合
     */
    List<Integer> selectRoleIdsByResId(@Param("resId") Integer resId);

}