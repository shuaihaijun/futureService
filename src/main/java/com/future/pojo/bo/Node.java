package com.future.pojo.bo;

import lombok.Data;

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
    private String id;
    /**
     * 父类节点ID
     */
    private String pid;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 子类节点列表
     */
    private List<Node> children;

    public Node() {
    }

    public Node(String id, String pid, String name) {
        super();
        this.id = id;
        this.pid = pid;
        this.name = name;
    }
}