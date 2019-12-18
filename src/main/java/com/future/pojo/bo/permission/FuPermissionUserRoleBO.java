package com.future.pojo.bo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关系信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("用户角色关系信息BO类")
@Data
public class FuPermissionUserRoleBO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String username;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 PIB、6 MIB、7 IB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer userType;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    /**
     * 角色ID集合
     */
    @ApiModelProperty(value = "角色ID集合")
    private List<Integer> roleIds;
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
     * 级别1、超级管理员 2、管理员 3、普通用户
     */
    @ApiModelProperty(value = "角色级别")
    private Integer roleLevel;
    /**
     * 角色所关联的工程项目KEY
     */
    @ApiModelProperty(value = "角色所关联的工程项目KEY")
    private Integer projKey;
    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;
    /**
     * 角色 0、无效 1、有效
     */
    @ApiModelProperty(value = "角色状态  0：无效  1：有效")
    private Integer roleStatus;
    /**
     * 角色标识  1：特殊角色  2：普通角色
     */
    @ApiModelProperty(value = "角色标识  1：特殊角色  2：普通角色")
    private Integer roleSign;

    public String createDate;
    public String modifyDate;

}