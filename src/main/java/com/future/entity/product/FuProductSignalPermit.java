package com.future.entity.product;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuProductSignalPermit {

    public static final String SIGNAL_ID = "signal_Id";
    public static final String PROJ_KEY = "proj_Key";
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 信号源ID
     */
    private Integer signalId;

    /**
     * 项目工程key
     */
    private Integer projKey;

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
     * 信号源ID
     * @return signal_id 信号源ID
     */
    public Integer getSignalId() {
        return signalId;
    }

    /**
     * 信号源ID
     * @param signalId 信号源ID
     */
    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    /**
     * 项目工程key
     * @return proj_key 项目工程key
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * 项目工程key
     * @param projKey 项目工程key
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
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