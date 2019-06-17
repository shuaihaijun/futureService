package com.future.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.future.pojo.bo.Node;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;


/**
 * 构造树状结构工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class TreeBuilder {

    List<Node> nodes = new ArrayList<Node>();

    public TreeBuilder() {
    }

    public TreeBuilder(List<Node> nodes) {
        super();
        this.nodes = nodes;
    }

    public String buildTree(List<Node> nodes) {
        TreeBuilder treeBuilder = new TreeBuilder(nodes);
        return treeBuilder.buildJSONTree();
    }

    /**
     * 构建JSON树形结构
     */
    private String buildJSONTree() {
        List<Node> nodeTree = buildTree();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(nodeTree));
        return jsonArray.toString();
    }

    /**
     * 构建树形结构
     *
     * @return
     */
    private List<Node> buildTree() {
        List<Node> treeNodes = new ArrayList<>();
        List<Node> rootNodes = getRootNodes();
        for (Node rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    /**
     * 递归子节点
     *
     * @param node
     */
    public void buildChildNodes(Node node) {
        List<Node> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for (Node child : children) {
                buildChildNodes(child);
            }
            node.setChildren(children);
        }
    }

    /**
     * 获取父节点下所有的子节点
     *
     * @param pnode
     * @return
     */
    public List<Node> getChildNodes(Node pnode) {
        List<Node> childNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (pnode.getId().equals(n.getPid())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    /**
     * 通过父节点Id查询相关的子节点
     *
     * @param pids
     * @return
     */
    public List<String> getChildNodes(List<String> pids) {
        List<String> chileNodes = Lists.newArrayList();
        for (String pid : pids) {
            if (parentNode(pid)) {
                for (Node n : nodes) {
                    System.out.println("该节点的ID是：" + n.getId() + "该节点的父节点是：" + n.getPid() + "====" + "与参数节点比较真假：" + pid.equals(n.getPid()));
                    if (pid.equals(n.getPid())) {
                        chileNodes.add(n.getId());
                    }
                }
            }
            chileNodes.add(pid);
        }

        return chileNodes;
    }

    /**
     * 通过ID判断是否为根节点
     *
     * @param id 树形节点ID
     * @return
     */
    public boolean parentNode(String id) {
        boolean isRootNode = false;
        for (Node n : nodes) {
            System.out.println("该节点的ID是：" + n.getId() + "====" + "父ID是：" + n.getPid());
            if (id.equals(n.getPid())) {
                isRootNode = true;
                break;
            }
        }
        return isRootNode;
    }

    /**
     * 判断是否为根节点
     *
     * @param node
     * @return
     */
    public boolean rootNode(Node node) {
        boolean isRootNode = true;
        for (Node n : nodes) {
            if (node.getPid().equals(n.getId())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }

    /**
     * 获取集合中所有的根节点
     *
     * @return
     */
    public List<Node> getRootNodes() {
        List<Node> rootNodes = new ArrayList<>();
        for (Node n : nodes) {
            if (rootNode(n)) {
                rootNodes.add(n);
            }
        }
        return rootNodes;
    }
}