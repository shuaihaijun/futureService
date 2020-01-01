package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuPermissionRole {


    public static String PROJ_KEY= "proj_Key";
    public static String ROLE_DEFAULT= "role_default";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 级别1、超级管理员 2、管理员 3、普通用户
     */
    private Integer roleLevel;

    /**
     * 角色状态: 0无效 1有效
     */
    private Integer roleStatus;

    /**
     * 角色类型 1、特殊角色 2、普通角色
     */
    private Integer roleSign;

    /**
     * 所属工程项目key即fu_permission_project.proj_key
     */
    private Integer projKey;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 创建人姓名
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 是否为新用户默认角色 0、否 1、是
     */
    private Integer roleDefault;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 角色代码
     * @return role_code 角色代码
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 角色代码
     * @param roleCode 角色代码
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /**
     * 角色名称
     * @return role_name 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 角色名称
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 级别1、超级管理员 2、管理员 3、普通用户
     * @return role_level 级别1、超级管理员 2、管理员 3、普通用户
     */
    public Integer getRoleLevel() {
        return roleLevel;
    }

    /**
     * 级别1、超级管理员 2、管理员 3、普通用户
     * @param roleLevel 级别1、超级管理员 2、管理员 3、普通用户
     */
    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    /**
     * 角色状态: 0无效 1有效
     * @return role_status 角色状态: 0无效 1有效
     */
    public Integer getRoleStatus() {
        return roleStatus;
    }

    /**
     * 角色状态: 0无效 1有效
     * @param roleStatus 角色状态: 0无效 1有效
     */
    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    /**
     * 角色类型 1、特殊角色 2、普通角色
     * @return role_sign 角色类型 1、特殊角色 2、普通角色
     */
    public Integer getRoleSign() {
        return roleSign;
    }

    /**
     * 角色类型 1、特殊角色 2、普通角色
     * @param roleSign 角色类型 1、特殊角色 2、普通角色
     */
    public void setRoleSign(Integer roleSign) {
        this.roleSign = roleSign;
    }

    /**
     * 所属工程项目key即fu_permission_project.proj_key
     * @return proj_key 所属工程项目key即fu_permission_project.proj_key
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * 所属工程项目key即fu_permission_project.proj_key
     * @param projKey 所属工程项目key即fu_permission_project.proj_key
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * 角色描述
     * @return role_desc 角色描述
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * 角色描述
     * @param roleDesc 角色描述
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    /**
     * 创建人姓名
     * @return creater 创建人姓名
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 创建人姓名
     * @param creater 创建人姓名
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 修改时间
     * @return modify_date 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 修改时间
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 是否为新用户默认角色 0、否 1、是
     * @return role_default 是否为新用户默认角色 0、否 1、是
     */
    public Integer getRoleDefault() {
        return roleDefault;
    }

    /**
     * 是否为新用户默认角色 0、否 1、是
     * @param roleDefault 是否为新用户默认角色 0、否 1、是
     */
    public void setRoleDefault(Integer roleDefault) {
        this.roleDefault = roleDefault;
    }
}