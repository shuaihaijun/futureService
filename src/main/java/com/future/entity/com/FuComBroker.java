package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class FuComBroker {


    public static String BROKER_NAME="broker_name";
    public static String BROKER_ID="id";
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 经纪人名称
     */
    private String brokerName;

    /**
     * 经纪人ID
     */
    private String brokerId;

    /**
     * 经纪商类型（0 合作，1 其他）
     */
    private Integer brokerType;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 说明
     */
    private String comment;

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
     * 经纪人名称
     * @return broker_name 经纪人名称
     */
    public String getBrokerName() {
        return brokerName;
    }

    /**
     * 经纪人名称
     * @param brokerName 经纪人名称
     */
    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName == null ? null : brokerName.trim();
    }

    /**
     * 经纪人ID
     * @return broker_id 经纪人ID
     */
    public String getBrokerId() {
        return brokerId;
    }

    /**
     * 经纪人ID
     * @param brokerId 经纪人ID
     */
    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId == null ? null : brokerId.trim();
    }

    /**
     * 经纪商类型（0 合作，1 其他）
     * @return broker_type 经纪商类型（0 合作，1 其他）
     */
    public Integer getBrokerType() {
        return brokerType;
    }

    /**
     * 经纪商类型（0 合作，1 其他）
     * @param brokerType 经纪商类型（0 合作，1 其他）
     */
    public void setBrokerType(Integer brokerType) {
        this.brokerType = brokerType;
    }

    /**
     * 分组名称
     * @return group_name 分组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 分组名称
     * @param groupName 分组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * 说明
     * @return comment 说明
     */
    public String getComment() {
        return comment;
    }

    /**
     * 说明
     * @param comment 说明
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}