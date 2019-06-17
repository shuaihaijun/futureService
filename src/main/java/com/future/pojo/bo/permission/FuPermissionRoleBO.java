package com.future.pojo.bo.permission;

import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("角色信息BO类")
@Data
public class FuPermissionRoleBO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 角色所关联的工程项目KEY
     */
    @ApiModelProperty(value = "角色所关联的工程项目KEY")
    private Integer projKey;
    /**
     * 角色代码
     */
    @ApiModelProperty(value = "角色代码")
    private String roleCode;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /**
     * 角色级别1、超级管理员 2、管理员 3、普通用户
     */
    @ApiModelProperty(value = "角色级别")
    private Integer roleLevel;
    /**
     * 角色标识  1：特殊角色  2：普通角色
     */

    @ApiModelProperty(value = "角色标识  1：特殊角色  2：普通角色")
    private Integer roleSign;

    /**
     * 角色权限复制时的角色ID集合
     */
    @ApiModelProperty(value = "权限复制时的角色ID集合")
    private List<Integer> roleIds;
    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id")
    private Integer userId;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String creater;

    /**
     * 将业务层对象转换为数据访问实体对象
     *
     * @param fuPermissionRoleBO 业务层对象
     * @return 数据访问实体对象
     */
    public static FuPermissionRole boToModel(FuPermissionRoleBO fuPermissionRoleBO) {
        return boToModel(fuPermissionRoleBO, null);
    }

    /**
     * 将业务层对象转换为数据访问实体对象
     *
     * @param fuPermissionRoleBO 业务层对象
     * @param permissionRole   数据访问实体对象
     * @return 数据访问实体对象
     */
    public static FuPermissionRole boToModel(FuPermissionRoleBO fuPermissionRoleBO, FuPermissionRole permissionRole) {
        if (fuPermissionRoleBO == null) {
            return null;
        }
        if (permissionRole == null) {
            permissionRole = new FuPermissionRole();
        }
        BeanUtil.copyProperties(fuPermissionRoleBO, permissionRole);
        return permissionRole;
    }
}