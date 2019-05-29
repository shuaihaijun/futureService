package com.future.entity.com;

import java.util.Date;

public class FuComMaterialArea {
    /**
     * 
     */
    private Integer id;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域介绍
     */
    private String areaIntroduce;

    /**
     * 区域状态（0 正常，1 删除）
     */
    private Byte areaState;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

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
     * 区域名称
     * @return area_name 区域名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 区域名称
     * @param areaName 区域名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**
     * 区域介绍
     * @return area_introduce 区域介绍
     */
    public String getAreaIntroduce() {
        return areaIntroduce;
    }

    /**
     * 区域介绍
     * @param areaIntroduce 区域介绍
     */
    public void setAreaIntroduce(String areaIntroduce) {
        this.areaIntroduce = areaIntroduce == null ? null : areaIntroduce.trim();
    }

    /**
     * 区域状态（0 正常，1 删除）
     * @return area_state 区域状态（0 正常，1 删除）
     */
    public Byte getAreaState() {
        return areaState;
    }

    /**
     * 区域状态（0 正常，1 删除）
     * @param areaState 区域状态（0 正常，1 删除）
     */
    public void setAreaState(Byte areaState) {
        this.areaState = areaState;
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
}