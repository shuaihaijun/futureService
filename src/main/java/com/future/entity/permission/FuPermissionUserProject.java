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
 * 用户工程项目关系信息实体类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("用户工程项目关系信息实体类")
@TableName("fu_permission_userProject")
@Data
public class FuPermissionUserProject implements Serializable {

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
    @TableField
    private Integer userId;
    /**
     * 所拥有的工程项目
     */
    @ApiModelProperty(value = "管理员用户所管理的工程项目KEY")
    @TableField
    private Integer projKey;
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

    public static final String USER_ID = "User_id";
}