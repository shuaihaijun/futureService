package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class FuComServerSlave {
    public static final String SERVER_NAME="server_name";
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
     * 服务器名称
     */
    private String serverName;

    /**
     * 从服务器名称（TradeMax-live1.e）
     */
    private String serverSlaveName;

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
     * 服务器名称
     * @return server_name 服务器名称
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * 服务器名称
     * @param serverName 服务器名称
     */
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    /**
     * 从服务器名称（TradeMax-live1.e）
     * @return server_slave_name 从服务器名称（TradeMax-live1.e）
     */
    public String getServerSlaveName() {
        return serverSlaveName;
    }

    /**
     * 从服务器名称（TradeMax-live1.e）
     * @param serverSlaveName 从服务器名称（TradeMax-live1.e）
     */
    public void setServerSlaveName(String serverSlaveName) {
        this.serverSlaveName = serverSlaveName == null ? null : serverSlaveName.trim();
    }
}