package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuComDictionary {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 字典标识
     */
    private String sign;

    /**
     * 字典key
     */
    private String key;

    /**
     * 字典值
     */
    private String value;

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
     * 字典标识
     * @return sign 字典标识
     */
    public String getSign() {
        return sign;
    }

    /**
     * 字典标识
     * @param sign 字典标识
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    /**
     * 字典key
     * @return key 字典key
     */
    public String getKey() {
        return key;
    }

    /**
     * 字典key
     * @param key 字典key
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     * 字典值
     * @return value 字典值
     */
    public String getValue() {
        return value;
    }

    /**
     * 字典值
     * @param value 字典值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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