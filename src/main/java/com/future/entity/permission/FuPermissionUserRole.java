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
 * 用户角色关系信息实体类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("用户角色关系信息实体类")
@Data
@TableName("fu_permission_userRole")

public class FuPermissionUserRole implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID")
    @TableField()
    private Integer userId;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色ID")
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

    public static final String ROLE_ID = "Role_id";
    public static final String USER_ID = "User_id";
}