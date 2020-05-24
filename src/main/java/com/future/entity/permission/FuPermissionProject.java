package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

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
     * 类型(0 团队,1 平台,2 系统)
     */
    private Integer projType;

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
     * 项目工程状态 1有效 0无效
     */
    private Integer projStatus;

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
     * crm域名
     */
    private String projCrmRealm;

    /**
     * 官网域名
     */
    private String projOfficialRealm;

    /**
     * 品牌名称
     */
    private String projBrandName;

    /**
     * 品牌描述
     */
    private String projBrandDesc;

    /**
     * 地址
     */
    private String projDress;

    /**
     * 项目工程logo_top
     */
    private String projLogoTop;

    /**
     * 项目工程logo_down
     */
    private String projLogoDown;

    /**
     * 二维码
     */
    private String projTwoCode;

    /**
     * 电话
     */
    private String projPhone;

    /**
     * qq号码
     */
    private String projQq;

    /**
     * 邮箱
     */
    private String projEmail;

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
     * crm域名
     * @return proj_crm_realm crm域名
     */
    public String getProjCrmRealm() {
        return projCrmRealm;
    }

    /**
     * crm域名
     * @param projCrmRealm crm域名
     */
    public void setProjCrmRealm(String projCrmRealm) {
        this.projCrmRealm = projCrmRealm == null ? null : projCrmRealm.trim();
    }

    /**
     * 官网域名
     * @return proj_official_realm 官网域名
     */
    public String getProjOfficialRealm() {
        return projOfficialRealm;
    }

    /**
     * 官网域名
     * @param projOfficialRealm 官网域名
     */
    public void setProjOfficialRealm(String projOfficialRealm) {
        this.projOfficialRealm = projOfficialRealm == null ? null : projOfficialRealm.trim();
    }

    /**
     * 品牌名称
     * @return proj_brand_name 品牌名称
     */
    public String getProjBrandName() {
        return projBrandName;
    }

    /**
     * 品牌名称
     * @param projBrandName 品牌名称
     */
    public void setProjBrandName(String projBrandName) {
        this.projBrandName = projBrandName == null ? null : projBrandName.trim();
    }

    /**
     * 品牌描述
     * @return proj_brand_desc 品牌描述
     */
    public String getProjBrandDesc() {
        return projBrandDesc;
    }

    /**
     * 品牌描述
     * @param projBrandDesc 品牌描述
     */
    public void setProjBrandDesc(String projBrandDesc) {
        this.projBrandDesc = projBrandDesc == null ? null : projBrandDesc.trim();
    }

    /**
     * 地址
     * @return proj_dress 地址
     */
    public String getProjDress() {
        return projDress;
    }

    /**
     * 地址
     * @param projDress 地址
     */
    public void setProjDress(String projDress) {
        this.projDress = projDress == null ? null : projDress.trim();
    }

    /**
     * 项目工程logo_top
     * @return proj_logo_top 项目工程logo_top
     */
    public String getProjLogoTop() {
        return projLogoTop;
    }

    /**
     * 项目工程logo_top
     * @param projLogoTop 项目工程logo_top
     */
    public void setProjLogoTop(String projLogoTop) {
        this.projLogoTop = projLogoTop == null ? null : projLogoTop.trim();
    }

    /**
     * 项目工程logo_down
     * @return proj_logo_down 项目工程logo_down
     */
    public String getProjLogoDown() {
        return projLogoDown;
    }

    /**
     * 项目工程logo_down
     * @param projLogoDown 项目工程logo_down
     */
    public void setProjLogoDown(String projLogoDown) {
        this.projLogoDown = projLogoDown == null ? null : projLogoDown.trim();
    }

    /**
     * 二维码
     * @return proj_two_code 二维码
     */
    public String getProjTwoCode() {
        return projTwoCode;
    }

    /**
     * 二维码
     * @param projTwoCode 二维码
     */
    public void setProjTwoCode(String projTwoCode) {
        this.projTwoCode = projTwoCode == null ? null : projTwoCode.trim();
    }

    /**
     * 电话
     * @return proj_phone 电话
     */
    public String getProjPhone() {
        return projPhone;
    }

    /**
     * 电话
     * @param projPhone 电话
     */
    public void setProjPhone(String projPhone) {
        this.projPhone = projPhone == null ? null : projPhone.trim();
    }

    /**
     * qq号码
     * @return proj_qq qq号码
     */
    public String getProjQq() {
        return projQq;
    }

    /**
     * qq号码
     * @param projQq qq号码
     */
    public void setProjQq(String projQq) {
        this.projQq = projQq == null ? null : projQq.trim();
    }

    /**
     * 邮箱
     * @return proj_email 邮箱
     */
    public String getProjEmail() {
        return projEmail;
    }

    /**
     * 邮箱
     * @param projEmail 邮箱
     */
    public void setProjEmail(String projEmail) {
        this.projEmail = projEmail == null ? null : projEmail.trim();
    }
}