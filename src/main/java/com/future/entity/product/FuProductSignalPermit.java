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
     * �ź�ԴID
     */
    private Integer signalId;

    /**
     * ��Ŀ����key
     */
    private Integer projKey;

    /**
     * ����ʱ��
     */
    private Date createDate;

    /**
     * �޸�ʱ��
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
     * �ź�ԴID
     * @return signal_id �ź�ԴID
     */
    public Integer getSignalId() {
        return signalId;
    }

    /**
     * �ź�ԴID
     * @param signalId �ź�ԴID
     */
    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    /**
     * ��Ŀ����key
     * @return proj_key ��Ŀ����key
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * ��Ŀ����key
     * @param projKey ��Ŀ����key
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
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
     * �޸�ʱ��
     * @return modify_date �޸�ʱ��
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * �޸�ʱ��
     * @param modifyDate �޸�ʱ��
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}