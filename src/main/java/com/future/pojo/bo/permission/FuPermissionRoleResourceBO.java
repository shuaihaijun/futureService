package com.future.pojo.bo.permission;

import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionRoleResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 角色权限资源关系信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("角色权限资源关系信息BO类")
@Data
public class FuPermissionRoleResourceBO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 功能权限主键
     */
    @ApiModelProperty(value = "权限资源主键")
    private Integer resId;
    /**
     * 角色主键
     */
    @ApiModelProperty(value = "角色主键")
    private Integer roleId;
    /**
     * 权限资源主键集合
     */
    @ApiModelProperty(value = "权限资源主键集合 ")
    private List<Integer> resIds;

    public static FuPermissionRoleResource boToModel(FuPermissionRoleResourceBO bo) {
        if (bo == null) {
            return null;
        }
        FuPermissionRoleResource permissionRoleResource = new FuPermissionRoleResource();
        BeanUtil.copyProperties(bo, permissionRoleResource);
        return permissionRoleResource;
    }
}