package com.zmy.util.node;

import com.zmy.util.component.Where;

/**
 * @ClassName: Node
 * @Author: zhangmy
 * @Description:
 * @Date: 2021-12-07 11:38
 * @Version: 1.0
 */
public class Node {

    private String rule;
    private Where<Object> connectWhere;
    private Boolean isClose;
    private Node preNode;
    private Node nextNode;

    public Node() {
    }

    public Node(String rule) {
        this.rule = rule;
        this.isClose = false;
    }

    public Node(String rule, Where<Object> connectWhere, Boolean isClose, Node preNode, Node nextNode) {
        this.rule = rule;
        this.connectWhere = connectWhere;
        this.isClose = isClose;
        this.preNode = preNode;
        this.nextNode = nextNode;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Where<Object> getConnectWhere() {
        return connectWhere;
    }

    public void setConnectWhere(Where<Object> connectWhere) {
        this.connectWhere = connectWhere;
    }

    public Boolean getIsClose() {
        return isClose;
    }

    public void setIsClose(Boolean isClose) {
        this.isClose = isClose;
    }

    public Node getPreNode() {
        return preNode;
    }

    public void setPreNode(Node preNode) {
        this.preNode = preNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + rule + '\'' +
                ", connectWhere=" + connectWhere +
                ", isClose=" + isClose +
                ", nextNode=" + nextNode +
                '}';
    }
}
