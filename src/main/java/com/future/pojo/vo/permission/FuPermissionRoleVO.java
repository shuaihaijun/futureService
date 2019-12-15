package com.future.pojo.vo.permission;

import com.baomidou.mybatisplus.annotations.TableField;
import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息VO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("角色信息VO类")
@Data
public class FuPermissionRoleVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
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
     * 系统名称
     */
    @ApiModelProperty(value = "工程项目名称")
    private String projName;
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
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String creater;
    /**
     * 新增时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 将数据访问实体对象转换为表现层对象
     *
     * @param permissionRole 数据访问实体对象
     * @return 表现层对象
     */
    public static FuPermissionRoleVO modelToVO(FuPermissionRole permissionRole) {
        if (permissionRole == null) {
            return null;
        }
        FuPermissionRoleVO fuPermissionRoleVO = new FuPermissionRoleVO();
        BeanUtil.copyProperties(permissionRole, fuPermissionRoleVO);
        return fuPermissionRoleVO;
    }
}