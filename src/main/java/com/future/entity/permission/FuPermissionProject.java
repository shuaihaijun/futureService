package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel("������Ŀ��Ϣʵ����")
@TableName("fu_permission_project")
public class FuPermissionProject {

    public static final String PROJ_ID = "Id";
    public static final String PROJ_KEY = "Proj_key";
    public static final String PROJ_STATUS = "Proj_status";
    /**
     * ����
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * ��Ŀ��������
     */
    private String projName;

    /**
     * ��Ŀ����KEY
     */
    private Integer projKey;

    /**
     * ��Ŀ���̹���Ա����
     */
    private String projAdmin;

    /**
     * ��Ŀ����״̬ 1��Ч 0��Ч
     */
    private Integer projStatus;

    /**
     * ����(0 �Ŷ�,1 ƽ̨,2 ϵͳ)
     */
    private Integer projType;

    /**
     * ����������
     */
    private String creater;

    /**
     * ����ʱ��
     */
    private Date createDate;

    /**
     * ����ʱ��
     */
    private Date modifyDate;

    /**
     * ��Ŀ����logo
     */
    private String projLogo;

    /**
     * ��Ŀ���̱���
     */
    private String projSlogan;

    /**
     * ��Ŀ��������
     */
    private String projDesc;

    /**
     * ����
     * @return id ����
     */
    public Integer getId() {
        return id;
    }

    /**
     * ����
     * @param id ����
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ��Ŀ��������
     * @return proj_name ��Ŀ��������
     */
    public String getProjName() {
        return projName;
    }

    /**
     * ��Ŀ��������
     * @param projName ��Ŀ��������
     */
    public void setProjName(String projName) {
        this.projName = projName == null ? null : projName.trim();
    }

    /**
     * ��Ŀ����KEY
     * @return proj_key ��Ŀ����KEY
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * ��Ŀ����KEY
     * @param projKey ��Ŀ����KEY
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * ��Ŀ���̹���Ա����
     * @return proj_admin ��Ŀ���̹���Ա����
     */
    public String getProjAdmin() {
        return projAdmin;
    }

    /**
     * ��Ŀ���̹���Ա����
     * @param projAdmin ��Ŀ���̹���Ա����
     */
    public void setProjAdmin(String projAdmin) {
        this.projAdmin = projAdmin == null ? null : projAdmin.trim();
    }

    /**
     * ��Ŀ����״̬ 1��Ч 0��Ч
     * @return proj_status ��Ŀ����״̬ 1��Ч 0��Ч
     */
    public Integer getProjStatus() {
        return projStatus;
    }

    /**
     * ��Ŀ����״̬ 1��Ч 0��Ч
     * @param projStatus ��Ŀ����״̬ 1��Ч 0��Ч
     */
    public void setProjStatus(Integer projStatus) {
        this.projStatus = projStatus;
    }

    /**
     * ����(0 �Ŷ�,1 ƽ̨,2 ϵͳ)
     * @return proj_type ����(0 �Ŷ�,1 ƽ̨,2 ϵͳ)
     */
    public Integer getProjType() {
        return projType;
    }

    /**
     * ����(0 �Ŷ�,1 ƽ̨,2 ϵͳ)
     * @param projType ����(0 �Ŷ�,1 ƽ̨,2 ϵͳ)
     */
    public void setProjType(Integer projType) {
        this.projType = projType;
    }

    /**
     * ����������
     * @return creater ����������
     */
    public String getCreater() {
        return creater;
    }

    /**
     * ����������
     * @param creater ����������
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * ����ʱ��
     * @return create_date ����ʱ��
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * ����ʱ��
     * @param createDate ����ʱ��
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * ����ʱ��
     * @return modify_date ����ʱ��
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * ����ʱ��
     * @param modifyDate ����ʱ��
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * ��Ŀ����logo
     * @return proj_logo ��Ŀ����logo
     */
    public String getProjLogo() {
        return projLogo;
    }

    /**
     * ��Ŀ����logo
     * @param projLogo ��Ŀ����logo
     */
    public void setProjLogo(String projLogo) {
        this.projLogo = projLogo == null ? null : projLogo.trim();
    }

    /**
     * ��Ŀ���̱���
     * @return proj_slogan ��Ŀ���̱���
     */
    public String getProjSlogan() {
        return projSlogan;
    }

    /**
     * ��Ŀ���̱���
     * @param projSlogan ��Ŀ���̱���
     */
    public void setProjSlogan(String projSlogan) {
        this.projSlogan = projSlogan == null ? null : projSlogan.trim();
    }

    /**
     * ��Ŀ��������
     * @return proj_desc ��Ŀ��������
     */
    public String getProjDesc() {
        return projDesc;
    }

    /**
     * ��Ŀ��������
     * @param projDesc ��Ŀ��������
     */
    public void setProjDesc(String projDesc) {
        this.projDesc = projDesc == null ? null : projDesc.trim();
    }
}