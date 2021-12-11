package com.zmy.util.node;

import com.zmy.util.component.Where;

/**
 * @ClassName: Node
 * @Author: zhangmy
 * @Description:
 * @Date: 2021-12-07 11:38
 * @Version: 1.0
 */
public class RuleNode {

    private String rule;
    private Where connectWhere;
    private Boolean isClose;
    private RuleNode preRuleNode;
    private RuleNode nextRuleNode;

    public RuleNode() {
    }

    public RuleNode(String rule) {
        this.rule = rule;
        this.isClose = false;
    }

    public RuleNode(String rule, Where connectWhere, Boolean isClose, RuleNode preRuleNode, RuleNode nextRuleNode) {
        this.rule = rule;
        this.connectWhere = connectWhere;
        this.isClose = isClose;
        this.preRuleNode = preRuleNode;
        this.nextRuleNode = nextRuleNode;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Where getConnectWhere() {
        return connectWhere;
    }

    public void setConnectWhere(Where connectWhere) {
        this.connectWhere = connectWhere;
    }

    public Boolean getIsClose() {
        return isClose;
    }

    public void setIsClose(Boolean isClose) {
        this.isClose = isClose;
    }

    public RuleNode getPreRuleNode() {
        return preRuleNode;
    }

    public void setPreRuleNode(RuleNode preRuleNode) {
        this.preRuleNode = preRuleNode;
    }

    public RuleNode getNextRuleNode() {
        return nextRuleNode;
    }

    public void setNextRuleNode(RuleNode nextRuleNode) {
        this.nextRuleNode = nextRuleNode;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + rule + '\'' +
                ", connectWhere=" + connectWhere +
                ", isClose=" + isClose +
                ", nextNode=" + nextRuleNode +
                '}';
    }
}
