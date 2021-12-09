package com.zmy.util.node;



import com.zmy.util.component.Where;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: RuleUtil
 * @Author: zhangmy
 * @Description:
 * @Date: 2021-12-07 11:40
 * @Version: 1.0
 */
public class RuleUtil {


    public static Where<Object> handler(RuleNode firstRuleNode, Map<String,Where<Object>> keyWhere) throws Exception {
        for (String s : keyWhere.keySet()) {
            firstRuleNode = split(firstRuleNode, s, keyWhere.get(s));
        }
        return merge(firstRuleNode);
    }

    /**
     * @Description: 重组链表，每次处理一个节点。具体操作为将原节点分割为2，替换原节点连接到链表上，
     * @Param: [firstNode, splitStr, connectStr]
     * @return: com.team.module.ucrud.crud.Node
     * @Author: zhangmy
     * @Date: 2021/2/22 14:31
     */
    private static RuleNode split(RuleNode firstRuleNode, String splitStr, Where<Object> connectWhere){
        boolean isCurrentNodeIsFirstNode = true;
        RuleNode newFirstRuleNode = firstRuleNode;
        RuleNode previousRuleNode = null;
        RuleNode nextRuleNode = firstRuleNode.getNextRuleNode();
        RuleNode currentRuleNode = firstRuleNode;
        while (!Objects.isNull(currentRuleNode)) {
            String value = currentRuleNode.getRule();
            if (value.contains(splitStr)) {
                //当前节点一分二，断开重组
                String[] split = value.split(splitStr);
                RuleNode ruleNode1 = new RuleNode(split[0]);
                ruleNode1.setConnectWhere(connectWhere);
                RuleNode ruleNode2 = new RuleNode(split[1]);
                ruleNode2.setConnectWhere(currentRuleNode.getConnectWhere());
                ruleNode1.setNextRuleNode(ruleNode2);
                ruleNode1.setPreRuleNode(previousRuleNode);
                ruleNode2.setNextRuleNode(nextRuleNode);
                ruleNode2.setPreRuleNode(ruleNode1);
                if (!Objects.isNull(previousRuleNode)) {
                    previousRuleNode.setNextRuleNode(ruleNode1);
                }
                if (!Objects.isNull(nextRuleNode)) {
                    nextRuleNode.setPreRuleNode(ruleNode2);
                }
                if (isCurrentNodeIsFirstNode) {
                    newFirstRuleNode = ruleNode1;
                }
                break;
            }
            previousRuleNode = currentRuleNode;
            currentRuleNode = currentRuleNode.getNextRuleNode();
            isCurrentNodeIsFirstNode = false;
            if (!Objects.isNull(currentRuleNode)) {
                nextRuleNode = currentRuleNode.getNextRuleNode();
            }
        }
        return newFirstRuleNode;
    }

    /**
     * @Description: 解析重组后的链表，将节点合并后返回最终合并的where
     * @Param: [firstNode]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2021/2/22 15:12//当前节点的where已经合并到前一个节点，删除当前节点，删除前节点的一个左括号，删除后节点的一个右括号
     */
    private static Where<Object> merge(RuleNode firstRuleNode) throws Exception {
        RuleNode currentRuleNode = firstRuleNode;
        while (!Objects.isNull(currentRuleNode)) {
            if (isNeedToJoin(currentRuleNode)) {//需要和前一个节点合并
                RuleNode preRuleNode = currentRuleNode.getPreRuleNode();
                /**
                 * 一共三步：
                 * 将该节点的where条件连接到前一个节点的where中
                 * 设置是否闭合 isClose
                 * 去括号
                 */
                //将该节点的where条件连接到前一个节点的where中
                Boolean isPreClose = preRuleNode.getIsClose();
                Boolean isCuClose = currentRuleNode.getIsClose();
                Where<Object> connectWhere = currentRuleNode.getConnectWhere();
                if (isAnd(currentRuleNode)) {
                    if (isPreClose && isCuClose) {
                        preRuleNode.getConnectWhere().partAndPart(connectWhere);
                    } else if (isPreClose) {
                        preRuleNode.getConnectWhere().partAnd(connectWhere);
                    } else if (isCuClose) {
                        preRuleNode.getConnectWhere().andPart(connectWhere);
                    } else {
                        preRuleNode.getConnectWhere().and(connectWhere);
                    }
                } else {
                    if (isPreClose && isCuClose) {
                        preRuleNode.getConnectWhere().partOrPart(connectWhere);
                    } else if (isPreClose) {
                        preRuleNode.getConnectWhere().partOr(connectWhere);
                    } else if (isCuClose) {
                        preRuleNode.getConnectWhere().orPart(connectWhere);
                    } else {
                        preRuleNode.getConnectWhere().or(connectWhere);
                    }
                }
                //设置是否闭合 isClose
                boolean isClose = isClose(currentRuleNode);
                preRuleNode.setIsClose(isClose);
                //去括号
                if (isClose) {
                    removeBracket(currentRuleNode);
                }
                preRuleNode.setNextRuleNode(currentRuleNode.getNextRuleNode());
                currentRuleNode.getNextRuleNode().setPreRuleNode(preRuleNode);
                currentRuleNode = preRuleNode;

            }
            currentRuleNode = currentRuleNode.getNextRuleNode();
        }
        if (getLength(firstRuleNode) == 2) {
            return firstRuleNode.getConnectWhere();
        } else {
            return merge(firstRuleNode);
        }
    }

    //判断当前节点是否需要和前一个节点合并
    private static boolean isNeedToJoin(RuleNode currentRuleNode) {
        if (Objects.isNull(currentRuleNode)) {
            return false;
        }
        String currentValue = currentRuleNode.getRule();
        String lowerCase = currentValue.toLowerCase();
        boolean result = false;
        if (lowerCase.contains("and") || lowerCase.contains("or")) {
            if (!lowerCase.contains("((") && !lowerCase.contains("))")) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 去点当前节点前面一个节点的一个结尾（  和   后面一个节点的一个开头）
     * @param currentRuleNode
     */
    private static void removeBracket(RuleNode currentRuleNode) {
        RuleNode preRuleNode = currentRuleNode.getPreRuleNode();
        RuleNode nextRuleNode = currentRuleNode.getNextRuleNode();
        preRuleNode.setRule(preRuleNode.getRule().trim().substring(0, preRuleNode.getRule().trim().length()-1));
        nextRuleNode.setRule(nextRuleNode.getRule().trim().substring(1));
    }

    private static boolean isClose(RuleNode currentRuleNode) {
        RuleNode preRuleNode = currentRuleNode.getPreRuleNode();
        RuleNode nextRuleNode = currentRuleNode.getNextRuleNode();
        if (preRuleNode.getRule().trim().endsWith("((") && nextRuleNode.getRule().trim().startsWith("))")) {
            return true;
        }
        return false;
    }

    private static boolean isAnd(RuleNode currentRuleNode) {
        String currentValue = currentRuleNode.getRule();
        String lowerCase = currentValue.toLowerCase();
        return lowerCase.contains("and");
    }

    private static int getLength(RuleNode firstRuleNode) {
        int length = 0;
        while (!Objects.isNull(firstRuleNode)) {
            length++;
            firstRuleNode = firstRuleNode.getNextRuleNode();
        }
        return length;
    }

}
