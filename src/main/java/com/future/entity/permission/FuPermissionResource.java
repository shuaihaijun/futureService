package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 权限资源信息实体类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("权限资源信息实体类")
@TableName("fu_permission_resource")
@Data
public class FuPermissionResource implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    //@TableId(value = "Id", type = IdType.AUTO)
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 权限资源父id
     */
    @ApiModelProperty(value = "权限资源父ID")
    @TableField
    private Integer resPid;
    /**
     * 权限资源名称
     */
    @ApiModelProperty(value = "权限资源名称")
    @TableField
    private String resName;
    /**
     * 权限资源Action
     */
    @ApiModelProperty(value = "权限资源ACTION")
    @TableField
    private String resAction;
    /**
     * 权限资源描述
     */
    @ApiModelProperty(value = "权限资源描述")
    @TableField
    private String resDesc;
    /**
     * 响应式图标
     */
    @ApiModelProperty(value = "响应式图标")
    @TableField()
    private String resIco;
    /**
     * 所属工程项目KEY
     */
    @ApiModelProperty(value = "所属工程项目KEY")
    @TableField
    private Integer projKey;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    @ApiModelProperty(value = "权限资源功能类型 1：菜单  2：按钮")
    @TableField(value = "Res_switchBut")
    private Integer resSwitchBut;
    /**
     * 权限资源状态1有效 0无效
     */
    @ApiModelProperty(value = "权限资源状态 1：有效 0：无效")
    @TableField
    private Integer resStatus;
    /**
     * 权限资源排序字段
     */
    @ApiModelProperty(value = "权限资源排序字段")
    @TableField
    private Integer resSort;
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    @TableField
    private String creater;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField
    private Date createDate;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField
    private Date modifyDate;

    public static final String RES_NAME = "Res_name";
    public static final String RES_PID = "Res_pid";
}