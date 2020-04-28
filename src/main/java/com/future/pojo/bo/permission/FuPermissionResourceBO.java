package com.future.pojo.bo.permission;

import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限资源信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("权限资源信息BO类")
@Data
public class FuPermissionResourceBO implements Serializable {

    private Integer userId;
    private Integer operUserId;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 权限资源父ID
     */
    @ApiModelProperty(value = "权限资源父ID")
    private Integer resPid;
    /**
     * 权限资源名称
     */
    @ApiModelProperty(value = "权限资源名称")
    private String resName;
    /**
     * 权限资源Action
     */
    @ApiModelProperty(value = "权限资源action")
    private String resAction;
    /**
     * 权限资源描述
     */
    @ApiModelProperty(value = "权限资源描述")
    private String resDesc;
    /**
     * 权限资源状态
     */
    @ApiModelProperty(value = "权限资源状态")
    private String resStatus;
    /**
     * 权限资源排序
     */
    @ApiModelProperty(value = "权限资源排序")
    private String resSort;
    /**
     * 响应式图标
     */
    @ApiModelProperty(value = "响应式图标")
    private String resIco;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creater;
    /**
     * 所属工程项目KEY
     */
    @ApiModelProperty(value = "所属工程项目KEY")
    private Integer projKey;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    @ApiModelProperty(value = "权限资源功能类型 1：菜单  2：按钮")
    private Integer resSwitchBut;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    @ApiModelProperty(value = "修改时间")
    private Date modifyDate;

    /**
     * 将业务层对象转换为数据访问实体对象
     *
     * @param fuPermissionResourceBO 业务层对象
     * @return 数据访问实体对象
     */
    public static FuPermissionResource boToModel(FuPermissionResourceBO fuPermissionResourceBO) {
        return boToModel(fuPermissionResourceBO, null);
    }

    /**
     * 将业务层对象转换为数据访问实体对象
     *
     * @param fuPermissionResourceBO 业务层对象
     * @param permissionResource             数据访问实体对象
     * @return 数据访问实体对象
     */
    public static FuPermissionResource boToModel(FuPermissionResourceBO fuPermissionResourceBO, FuPermissionResource permissionResource) {
        if (fuPermissionResourceBO == null) {
            return null;
        }
        if (permissionResource == null) {
            permissionResource = new FuPermissionResource();
        }
        BeanUtil.copyProperties(fuPermissionResourceBO, permissionResource);
        return permissionResource;
    }
}