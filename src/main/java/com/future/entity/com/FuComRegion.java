package com.future.entity.com;

public class FuComRegion {
    /**
     * 区域ID
     */
    private Short regionId;

    /**
     * 父区域ID
     */
    private Short parentId;

    /**
     * 区域名称
     */
    private String regionName;

    /**
     * 区域类型
     */
    private Boolean regionType;

    /**
     * 机构
     */
    private Short agencyId;

    /**
     * 区域ID
     * @return region_id 区域ID
     */
    public Short getRegionId() {
        return regionId;
    }

    /**
     * 区域ID
     * @param regionId 区域ID
     */
    public void setRegionId(Short regionId) {
        this.regionId = regionId;
    }

    /**
     * 父区域ID
     * @return parent_id 父区域ID
     */
    public Short getParentId() {
        return parentId;
    }

    /**
     * 父区域ID
     * @param parentId 父区域ID
     */
    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    /**
     * 区域名称
     * @return region_name 区域名称
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 区域名称
     * @param regionName 区域名称
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    /**
     * 区域类型
     * @return region_type 区域类型
     */
    public Boolean getRegionType() {
        return regionType;
    }

    /**
     * 区域类型
     * @param regionType 区域类型
     */
    public void setRegionType(Boolean regionType) {
        this.regionType = regionType;
    }

    /**
     * 机构
     * @return agency_id 机构
     */
    public Short getAgencyId() {
        return agencyId;
    }

    /**
     * 机构
     * @param agencyId 机构
     */
    public void setAgencyId(Short agencyId) {
        this.agencyId = agencyId;
    }
}