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
 * 工程项目信息实体类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("工程项目信息实体类")
@TableName("fu_permission_project")
@Data
public class FuPermissionProject implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 工程项目名称
     */
    @ApiModelProperty(value = "工程项目名称")
    @TableField
    private String projName;
    /**
     * 工程项目key，不可重复，全表唯一
     */
    @ApiModelProperty(value = "工程项目KEY")
    @TableField
    private Integer projKey;
    /**
     * 工程项目负责人姓名
     */
    @ApiModelProperty(value = "工程项目负责人姓名")
    @TableField
    private String projAdmin;
    /**
     * 状态 1 有效 0 无效
     */
    @ApiModelProperty(value = "状态 1：有效 0：无效")
    @TableField
    private Integer projStatus;

    /**
     * 类型 0普通系统 1特殊系统
     */
    @ApiModelProperty(value = "类型 0普通系统 1特殊系统")
    @TableField
    private Integer projType;
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

    public static final String PROJ_ID = "Id";
    public static final String PROJ_KEY = "Proj_key";
    public static final String PROJ_STATUS = "Proj_status";

}