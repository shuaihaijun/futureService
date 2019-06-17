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
 * 角色信息实体类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("角色信息实体类")
@TableName("fu_permission_role")
@Data
public class FuPermissionRole implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "角色")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 角色代码
     */
    @ApiModelProperty(value = "角色代码")
    @TableField()
    private String roleCode;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @TableField()
    private String roleName;
    /**
     * 级别1、超级管理员 2、管理员 3、普通用户
     */
    @ApiModelProperty(value = "角色级别")
    @TableField()
    private Integer roleLevel;
    /**
     * 角色 0、无效 1、有效
     */
    @ApiModelProperty(value = "角色状态  0：无效  1：有效")
    @TableField()
    private Integer roleStatus;
    /**
     * 角色标示 1、特殊角色 2、普通角色
     */
    @ApiModelProperty(value = "角色标识  1：特殊角色  2：普通角色")
    @TableField()
    private Integer roleSign;
    /**
     * 角色所关联的工程项目KEY
     */
    @ApiModelProperty(value = "角色所关联的系统标识")
    @TableField()
    private Integer projKey;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @TableField()
    private String roleDesc;
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
}