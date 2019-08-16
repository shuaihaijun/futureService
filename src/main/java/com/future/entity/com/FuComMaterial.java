package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuComMaterial {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 素材类型（0 文本，1 图片，2 文件，3 视频，4 链接）
     */
    private int materialType;

    /**
     * 素材主题
     */
    private String materialTitle;

    /**
     * 素材状态（0 发布，1 暂存，2 待审核，3 下架，4 删除）
     */
    private int materialState;

    /**
     * 素材区域ID
     */
    private Integer materialArea;

    /**
     * 创建人ID
     */
    private Integer createUser;

    /**
     * 审核人ID
     */
    private Integer checkUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 素材内容
     */
    private String materialData;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 素材类型（0 文本，1 图片，2 文件，3 视频，4 链接）
     * @return material_type 素材类型（0 文本，1 图片，2 文件，3 视频，4 链接）
     */
    public int getMaterialType() {
        return materialType;
    }

    /**
     * 素材类型（0 文本，1 图片，2 文件，3 视频，4 链接）
     * @param materialType 素材类型（0 文本，1 图片，2 文件，3 视频，4 链接）
     */
    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    /**
     * 素材主题
     * @return material_title 素材主题
     */
    public String getMaterialTitle() {
        return materialTitle;
    }

    /**
     * 素材主题
     * @param materialTitle 素材主题
     */
    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle == null ? null : materialTitle.trim();
    }

    /**
     * 素材状态（0 发布，1 暂存，2 待审核，3 下架，4 删除）
     * @return material_state 素材状态（0 发布，1 暂存，2 待审核，3 下架，4 删除）
     */
    public int getMaterialState() {
        return materialState;
    }

    /**
     * 素材状态（0 发布，1 暂存，2 待审核，3 下架，4 删除）
     * @param materialState 素材状态（0 发布，1 暂存，2 待审核，3 下架，4 删除）
     */
    public void setMaterialState(int materialState) {
        this.materialState = materialState;
    }

    /**
     * 素材区域ID
     * @return material_area 素材区域ID
     */
    public Integer getMaterialArea() {
        return materialArea;
    }

    /**
     * 素材区域ID
     * @param materialArea 素材区域ID
     */
    public void setMaterialArea(Integer materialArea) {
        this.materialArea = materialArea;
    }

    /**
     * 创建人ID
     * @return create_user 创建人ID
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 创建人ID
     * @param createUser 创建人ID
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 审核人ID
     * @return check_user 审核人ID
     */
    public Integer getCheckUser() {
        return checkUser;
    }

    /**
     * 审核人ID
     * @param checkUser 审核人ID
     */
    public void setCheckUser(Integer checkUser) {
        this.checkUser = checkUser;
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
     * 素材内容
     * @return material_data 素材内容
     */
    public String getMaterialData() {
        return materialData;
    }

    /**
     * 素材内容
     * @param materialData 素材内容
     */
    public void setMaterialData(String materialData) {
        this.materialData = materialData == null ? null : materialData.trim();
    }
}