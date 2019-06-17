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
 * 角色权限资源关系信息实体类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("角色权限资源关系信息实体类")
@TableName("fu_permission_roleResource")
@Data
public class FuPermissionRoleResource implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 功能权限主键
     */
    @ApiModelProperty(value = "权限资源主键")
    @TableField()
    private Integer resId;

    /**
     * 角色主键
     */
    @ApiModelProperty(value = "角色主键")
    @TableField()
    private Integer roleId;
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

    public static final String RES_ID = "Res_id";
    public static final String ROLE_ID = "Role_id";
}