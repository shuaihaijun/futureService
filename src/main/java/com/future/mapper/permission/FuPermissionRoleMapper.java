package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionRole;
import com.future.pojo.bo.permission.FuPermissionRoleBO;
import com.future.pojo.vo.permission.FuPermissionRoleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色模块Mapper接口
 *
 * @author Admin
 * @version: 1.0
 */
@Repository
public interface FuPermissionRoleMapper extends BaseMapper<FuPermissionRole> {

    /**
     * 通过主键集合删除角色信息
     *
     * @param ids 主键集合
     * @return 删除的条数
     */
    int deleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 分页查询，超级管理员
     *
     * @param fuPermissionRoleBO 查询参数对象
     * @return 分页数据
     */
    List<FuPermissionRoleVO> selectAllPageList(@Param("roleBO") FuPermissionRoleBO fuPermissionRoleBO);

    /**
     * 通过工程项目KEY查询所关联的角色信息集合
     *
     * @param projKey 工程项目KEY
     * @return 角色信息集合
     */
    List<FuPermissionRoleVO> selectByProjKey(@Param("projKey") Integer projKey);

    /**
     * 通过工程项目KEY集合查询所关联的角色信息集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 角色信息集合
     */
    List<FuPermissionRoleVO> selectByProjKeys(@Param("projKeys") List<Integer> projKeys);

    /**
     * 通过角色名称以及主键集合查询所关联的角色信息集合
     *
     * @param roleName 角色名称
     * @param ids      主键集合
     * @return 角色信息集合
     */
    List<FuPermissionRoleVO> selectByRoleNameAndIds(@Param("roleName") String roleName, @Param("ids") List<Integer> ids);

    /**
     * 分页查询
     *
     * @param fuPermissionRoleBO 查询参数对象
     * @param projKeys         工程项目KEY集合
     * @return 分页数据
     */
    List<FuPermissionRoleVO> selectPageList(@Param("roleBO") FuPermissionRoleBO fuPermissionRoleBO, @Param("projKeys") List<Integer> projKeys);

}