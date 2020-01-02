package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class FuComServer {

    public static final String SERVER_ID="id";
    public static final String SERVER_NAME="server_name";
    public static final String SERVER_STATE="server_state";
    public static final String BROKER_NAME="broker_name";
    public static final String SERVER_IP="server_ip";//MT5需要
    public static final String LOCAL_PORT="local_port";
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 经纪人ID
     */
    private Integer brokerId;

    /**
     * 服务器状态（0 正常，1 隐藏，2 作废）
     */
    private Integer serverState;

    /**
     * 经纪人名称
     */
    private String brokerName;

    /**
     * 服务器ip地址
     */
    private String serverIp;

    /**
     * 端口
     */
    private Integer serverPort;

    /**
     * 是否为主服务器
     */
    private Integer isChief;

    /**
     * 参数
     */
    private String params;

    /**
     * 本地端口
     */
    private Integer localPort;

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
     * 经纪人ID
     * @return broker_id 经纪人ID
     */
    public Integer getBrokerId() {
        return brokerId;
    }

    /**
     * 经纪人ID
     * @param brokerId 经纪人ID
     */
    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    /**
     * 信号源状态（0 正常，1 隐藏，2 作废）
     * @return server_state 信号源状态（0 正常，1 隐藏，2 作废）
     */
    public Integer getServerState() {
        return serverState;
    }

    /**
     * 信号源状态（0 正常，1 隐藏，2 作废）
     * @param serverState 信号源状态（0 正常，1 隐藏，2 作废）
     */
    public void setServerState(Integer serverState) {
        this.serverState = serverState;
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
     * 服务器ip地址
     * @return server_ip 服务器ip地址
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * 服务器ip地址
     * @param serverIp 服务器ip地址
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    /**
     * 端口
     * @return server_port 端口
     */
    public Integer getServerPort() {
        return serverPort;
    }

    /**
     * 端口
     * @param serverPort 端口
     */
    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * 是否为主服务器
     * @return is_chief 是否为主服务器
     */
    public Integer getIsChief() {
        return isChief;
    }

    /**
     * 是否为主服务器
     * @param isChief 是否为主服务器
     */
    public void setIsChief(Integer isChief) {
        this.isChief = isChief;
    }

    /**
     * 参数
     * @return params 参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 参数
     * @param params 参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 本地端口
     * @return local_port 本地端口
     */
    public Integer getLocalPort() {
        return localPort;
    }

    /**
     * 本地端口
     * @param localPort 本地端口
     */
    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }
}