package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionResource;
import com.future.pojo.bo.Node;
import com.future.pojo.bo.permission.FuPermissionResourceBO;
import com.future.pojo.vo.permission.FuPermissionResourceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限资源模块Mapper接口
 *
 * @author Admin
 * @version: 1.0
 */
@Repository
public interface FuPermissionResourceMapper extends BaseMapper<FuPermissionResource> {

    /**
     * 通过工程项目KEY集合查询权限资源信息集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 权限资源信息集合
     */
    List<FuPermissionResource> selectByProjKeys(@Param("projKeys") List<Integer> projKeys);

    /**
     * 通过角色ID查询所关联的权限资源集合
     *
     * @param roleId 角色ID
     * @return 权限资源集合
     */
    List<FuPermissionResourceVO> selectByRoleId(@Param("roleId") Integer roleId);

    /**
     * 通过工程项目KEY集合查询相关联的权限资源ID集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 权限资源ID集合
     */
    List<Integer> selectResIdsByProjKeys(@Param("projKeys") List<Integer> projKeys);
    /**
     * 分页查询
     *
     * @param fuPermissionResourceBO 查询参数对象
     * @return 分页数据
     */
    List<FuPermissionResourceVO> selectPageList(@Param("resBO") FuPermissionResourceBO fuPermissionResourceBO, @Param("projKeys") List<Integer> projKeys);

    /**
     * 通过工程项目KEY集合查询树形节点集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 权限资源树形节点集合
     */
    List<Node> selectTreeNodeByProjKeysSort(@Param("projKeys") List<Integer> projKeys);

    /**
     * 通过工程项目KEY集合查询树形节点集合
     *
     * @param projKeys 工程项目KEY集合
     * @return 权限资源树形节点集合
     */
    List<Node> selectTreeNodeByProjKeys(@Param("projKeys") List<Integer> projKeys);


}