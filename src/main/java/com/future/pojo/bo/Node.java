package com.future.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 树形节点类
 *
 * @author Admin
 * @version: 1.0
 */
@Data
public class Node {

    /**
     * 节点ID
     */
    public String id;
    /**
     * 父类节点ID
     */
    public String pid;
    /**
     * 节点名称
     */
    public String name;
    /**
     * 子类节点列表
     */
    public List<Node> children;

    public Node() {
    }

    public Node(String id, String pid, String name) {
        super();
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.resPid = pid;
        this.resName = name;
    }

    /**
     * 权限资源父ID
     */
    private String resPid;
    /**
     * 权限资源名称
     */
    private String resName;
    /**
     * 权限资源Action
     */
    private String resAction;
    /**
     * 权限资源描述
     */
    private String resDesc;
    /**
     * 权限资源状态
     */
    private String resStatus;
    /**
     * 权限资源排序
     */
    private String resSort;
    /**
     * 响应式图标
     */
    private String resIco;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 所属工程项目KEY
     */
    private Integer projKey;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    private Integer resSwitchBut;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    private Date createDate;
    /**
     * 标示菜单按钮1菜单 2 按钮
     */
    private Date modifyDate;

}