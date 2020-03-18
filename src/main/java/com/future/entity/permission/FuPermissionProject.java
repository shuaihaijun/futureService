package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel("工程项目信息实体类")
@TableName("fu_permission_project")
public class FuPermissionProject {

    public static final String PROJ_ID = "Id";
    public static final String PROJ_KEY = "Proj_key";
    public static final String PROJ_STATUS = "Proj_status";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目工程名称
     */
    private String projName;

    /**
     * 项目工程KEY
     */
    private Integer projKey;

    /**
     * 项目工程管理员姓名
     */
    private String projAdmin;

    /**
     * 项目工程状态 1有效 0无效
     */
    private Integer projStatus;

    /**
     * 类型(0 团队,1 平台,2 系统)
     */
    private Integer projType;

    /**
     * 创建人姓名
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 项目工程logo
     */
    private String projLogo;

    /**
     * 项目工程标语
     */
    private String projSlogan;

    /**
     * 项目工程描述
     */
    private String projDesc;

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
     * 项目工程名称
     * @return proj_name 项目工程名称
     */
    public String getProjName() {
        return projName;
    }

    /**
     * 项目工程名称
     * @param projName 项目工程名称
     */
    public void setProjName(String projName) {
        this.projName = projName == null ? null : projName.trim();
    }

    /**
     * 项目工程KEY
     * @return proj_key 项目工程KEY
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * 项目工程KEY
     * @param projKey 项目工程KEY
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * 项目工程管理员姓名
     * @return proj_admin 项目工程管理员姓名
     */
    public String getProjAdmin() {
        return projAdmin;
    }

    /**
     * 项目工程管理员姓名
     * @param projAdmin 项目工程管理员姓名
     */
    public void setProjAdmin(String projAdmin) {
        this.projAdmin = projAdmin == null ? null : projAdmin.trim();
    }

    /**
     * 项目工程状态 1有效 0无效
     * @return proj_status 项目工程状态 1有效 0无效
     */
    public Integer getProjStatus() {
        return projStatus;
    }

    /**
     * 项目工程状态 1有效 0无效
     * @param projStatus 项目工程状态 1有效 0无效
     */
    public void setProjStatus(Integer projStatus) {
        this.projStatus = projStatus;
    }

    /**
     * 类型(0 团队,1 平台,2 系统)
     * @return proj_type 类型(0 团队,1 平台,2 系统)
     */
    public Integer getProjType() {
        return projType;
    }

    /**
     * 类型(0 团队,1 平台,2 系统)
     * @param projType 类型(0 团队,1 平台,2 系统)
     */
    public void setProjType(Integer projType) {
        this.projType = projType;
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
     * 更新时间
     * @return modify_date 更新时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 更新时间
     * @param modifyDate 更新时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 项目工程logo
     * @return proj_logo 项目工程logo
     */
    public String getProjLogo() {
        return projLogo;
    }

    /**
     * 项目工程logo
     * @param projLogo 项目工程logo
     */
    public void setProjLogo(String projLogo) {
        this.projLogo = projLogo == null ? null : projLogo.trim();
    }

    /**
     * 项目工程标语
     * @return proj_slogan 项目工程标语
     */
    public String getProjSlogan() {
        return projSlogan;
    }

    /**
     * 项目工程标语
     * @param projSlogan 项目工程标语
     */
    public void setProjSlogan(String projSlogan) {
        this.projSlogan = projSlogan == null ? null : projSlogan.trim();
    }

    /**
     * 项目工程描述
     * @return proj_desc 项目工程描述
     */
    public String getProjDesc() {
        return projDesc;
    }

    /**
     * 项目工程描述
     * @param projDesc 项目工程描述
     */
    public void setProjDesc(String projDesc) {
        this.projDesc = projDesc == null ? null : projDesc.trim();
    }
}