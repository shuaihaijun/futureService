package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class FuComNet {

    public static final String OWN_TYPE ="own_type";
    public static final String USE_ID ="use_Id";
    public static final String NET_TYPE ="net_Type";
    public static final String NET_ULR ="net_Url";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 使用者 （0公司数据，1 团队数据）
     */
    private Integer ownType;

    /**
     * 使用者ID
     */
    private Integer useId;

    /**
     * 地址类型 （0 官网， 1 crm，2 开户链接）
     */
    private Integer netType;

    /**
     * 描述
     */
    private String netDesc;

    /**
     * 链接地址
     */
    private String netUrl;

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
     * 使用者 （0公司数据，1 团队数据）
     * @return own_type 使用者 （0公司数据，1 团队数据）
     */
    public Integer getOwnType() {
        return ownType;
    }

    /**
     * 使用者 （0公司数据，1 团队数据）
     * @param ownType 使用者 （0公司数据，1 团队数据）
     */
    public void setOwnType(Integer ownType) {
        this.ownType = ownType;
    }

    /**
     * 使用者ID
     * @return use_id 使用者ID
     */
    public Integer getUseId() {
        return useId;
    }

    /**
     * 使用者ID
     * @param useId 使用者ID
     */
    public void setUseId(Integer useId) {
        this.useId = useId;
    }

    /**
     * 地址类型 （0 官网， 1 crm，2 开户链接）
     * @return net_type 地址类型 （0 官网， 1 crm，2 开户链接）
     */
    public Integer getNetType() {
        return netType;
    }

    /**
     * 地址类型 （0 官网， 1 crm，2 开户链接）
     * @param netType 地址类型 （0 官网， 1 crm，2 开户链接）
     */
    public void setNetType(Integer netType) {
        this.netType = netType;
    }

    /**
     * 描述
     * @return net_desc 描述
     */
    public String getNetDesc() {
        return netDesc;
    }

    /**
     * 描述
     * @param netDesc 描述
     */
    public void setNetDesc(String netDesc) {
        this.netDesc = netDesc == null ? null : netDesc.trim();
    }

    /**
     * 链接地址
     * @return net_url 链接地址
     */
    public String getNetUrl() {
        return netUrl;
    }

    /**
     * 链接地址
     * @param netUrl 链接地址
     */
    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl == null ? null : netUrl.trim();
    }
}