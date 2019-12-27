package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuComDictionary {

    public static String DIC_ID="id";
    public static String DIC_SIGN="dic_sign";
    public static String DIC_NAME="dic_name";
    public static String DIC_KEY="dic_key";
    public static String DIC_VALUE="dic_value";
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 字典标识
     */
    private String dicSign;

    /**
     * 名称
     */
    private String dicName;

    /**
     * 字典key
     */
    private Integer dicKey;

    /**
     * 字典值
     */
    private String dicValue;

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
     * @return dic_sign 字典标识
     */
    public String getDicSign() {
        return dicSign;
    }

    /**
     * 字典标识
     * @param dicSign 字典标识
     */
    public void setDicSign(String dicSign) {
        this.dicSign = dicSign == null ? null : dicSign.trim();
    }

    /**
     * 名称
     * @return dic_name 名称
     */
    public String getDicName() {
        return dicName;
    }

    /**
     * 名称
     * @param dicName 名称
     */
    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    /**
     * 字典key
     * @return dic_key 字典key
     */
    public Integer getDicKey() {
        return dicKey;
    }

    /**
     * 字典key
     * @param dicKey 字典key
     */
    public void setDicKey(Integer dicKey) {
        this.dicKey = dicKey;
    }

    /**
     * 字典值
     * @return dic_value 字典值
     */
    public String getDicValue() {
        return dicValue;
    }

    /**
     * 字典值
     * @param dicValue 字典值
     */
    public void setDicValue(String dicValue) {
        this.dicValue = dicValue == null ? null : dicValue.trim();
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