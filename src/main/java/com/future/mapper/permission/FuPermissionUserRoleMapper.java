package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionUserRole;
import com.future.pojo.bo.permission.FuPermissionUserRoleBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户角色关联信息模块Mapper接口
 *
 * @author Admin
 * @version: 1.0
 */
@Repository
public interface FuPermissionUserRoleMapper extends BaseMapper<FuPermissionUserRole> {

    /**
     * 通过用户ID查询所关联的角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    List<Integer> selectRoleIdsByUserId(Integer userId);

    /**
     * 查询所有的用户ID集合并去重
     *
     * @return 用户ID集合
     */
    List<Integer> selectUserIds();

    /**
     * 通过角色ID集合查询所关联的用户ID集合并去重
     *
     * @param roleIds 角色ID集合
     * @return 用户ID集合
     */
    List<Integer> selectUserIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);

    /**
     * 根据条件查询用户角色信息
     * @param condition 条件集合
     * @return 用户角色集合
     */
    List<FuPermissionUserRoleBO> queryUserRole(Map condition);

}