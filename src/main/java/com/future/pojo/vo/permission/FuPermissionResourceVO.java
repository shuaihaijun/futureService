package com.future.pojo.vo.permission;

import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限资源信息VO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("权限资源信息VO类")
@Data
public class FuPermissionResourceVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 权限资源父id
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
     * 响应式图标
     */
    @ApiModelProperty(value = "响应式图标")
    private String resIco;
    /**
     * 所属工程项目KEY
     */
    @ApiModelProperty(value = "所属工程项目KEY")
    private Integer projKey;
    /**
     * 所属工程项目
     */
    @ApiModelProperty(value = "所属工程项目名称")
    private String projName;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    @ApiModelProperty(value = "权限资源功能类型 1：菜单  2：按钮")
    private Integer resSwitchBut;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String creater;
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
     * 新增时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date modifyDate;

    /**
     * 将数据访问实体对象转换为表现层对象
     *
     * @param permissionResource 数据访问实体对象
     * @return 表现层对象
     */
    public static FuPermissionResourceVO modelToVO(FuPermissionResource permissionResource) {
        if (permissionResource == null) {
            return null;
        }
        FuPermissionResourceVO fuPermissionResourceVO = new FuPermissionResourceVO();
        BeanUtil.copyProperties(permissionResource, fuPermissionResourceVO);
        return fuPermissionResourceVO;
    }
}