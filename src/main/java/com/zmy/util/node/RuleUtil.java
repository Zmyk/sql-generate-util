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


    public static Where<Object> handler(Node firstNode, Map<String,Where<Object>> keyWhere) {
        for (String s : keyWhere.keySet()) {
            firstNode = split(firstNode, s, keyWhere.get(s));
        }
        return merge(firstNode);
    }

    /**
     * @Description: 重组链表，每次处理一个节点。具体操作为将原节点分割为2，替换原节点连接到链表上，
     * @Param: [firstNode, splitStr, connectStr]
     * @return: com.team.module.ucrud.crud.Node
     * @Author: zhangmy
     * @Date: 2021/2/22 14:31
     */
    private static Node split(Node firstNode, String splitStr, Where<Object> connectWhere){
        boolean isCurrentNodeIsFirstNode = true;
        Node newFirstNode = firstNode;
        Node previousNode = null;
        Node nextNode = firstNode.getNextNode();
        Node currentNode = firstNode;
        while (!Objects.isNull(currentNode)) {
            String value = currentNode.getRule();
            if (value.contains(splitStr)) {
                //当前节点一分二，断开重组
                String[] split = value.split(splitStr);
                Node node1 = new Node(split[0]);
                node1.setConnectWhere(connectWhere);
                Node node2 = new Node(split[1]);
                node2.setConnectWhere(currentNode.getConnectWhere());
                node1.setNextNode(node2);
                node1.setPreNode(previousNode);
                node2.setNextNode(nextNode);
                node2.setPreNode(node1);
                if (!Objects.isNull(previousNode)) {
                    previousNode.setNextNode(node1);
                }
                if (!Objects.isNull(nextNode)) {
                    nextNode.setPreNode(node2);
                }
                if (isCurrentNodeIsFirstNode) {
                    newFirstNode = node1;
                }
                break;
            }
            previousNode = currentNode;
            currentNode = currentNode.getNextNode();
            isCurrentNodeIsFirstNode = false;
            if (!Objects.isNull(currentNode)) {
                nextNode = currentNode.getNextNode();
            }
        }
        return newFirstNode;
    }

    /**
     * @Description: 解析重组后的链表，将节点合并后返回最终合并的where
     * @Param: [firstNode]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2021/2/22 15:12//当前节点的where已经合并到前一个节点，删除当前节点，删除前节点的一个左括号，删除后节点的一个右括号
     */
    private static Where<Object> merge(Node firstNode) {
        Node currentNode = firstNode;
        while (!Objects.isNull(currentNode)) {
            if (isNeedToJoin(currentNode)) {//需要和前一个节点合并
                Node preNode = currentNode.getPreNode();
                /**
                 * 一共三步：
                 * 将该节点的where条件连接到前一个节点的where中
                 * 设置是否闭合 isClose
                 * 去括号
                 */
                //将该节点的where条件连接到前一个节点的where中
                Boolean isPreClose = preNode.getIsClose();
                Boolean isCuClose = currentNode.getIsClose();
                Where<Object> connectWhere = currentNode.getConnectWhere();
                if (isAnd(currentNode)) {
                    if (isPreClose && isCuClose) {
                        preNode.getConnectWhere().partAndPart(connectWhere);
                    } else if (isPreClose) {
                        preNode.getConnectWhere().partAnd(connectWhere);
                    } else if (isCuClose) {
                        preNode.getConnectWhere().andPart(connectWhere);
                    } else {
                        preNode.getConnectWhere().and(connectWhere);
                    }
                } else {
                    if (isPreClose && isCuClose) {
                        preNode.getConnectWhere().partOrPart(connectWhere);
                    } else if (isPreClose) {
                        preNode.getConnectWhere().partOr(connectWhere);
                    } else if (isCuClose) {
                        preNode.getConnectWhere().orPart(connectWhere);
                    } else {
                        preNode.getConnectWhere().or(connectWhere);
                    }
                }
                //设置是否闭合 isClose
                boolean isClose = isClose(currentNode);
                preNode.setIsClose(isClose);
                //去括号
                if (isClose) {
                    removeBracket(currentNode);
                }
                preNode.setNextNode(currentNode.getNextNode());
                currentNode.getNextNode().setPreNode(preNode);
                currentNode = preNode;

            }
            currentNode = currentNode.getNextNode();
        }
        if (getLength(firstNode) == 2) {
            return firstNode.getConnectWhere();
        } else {
            return merge(firstNode);
        }
    }

    //判断当前节点是否需要和前一个节点合并
    private static boolean isNeedToJoin(Node currentNode) {
        if (Objects.isNull(currentNode)) {
            return false;
        }
        String currentValue = currentNode.getRule();
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
     * @param currentNode
     */
    private static void removeBracket(Node currentNode) {
        Node preNode = currentNode.getPreNode();
        Node nextNode = currentNode.getNextNode();
        preNode.setRule(preNode.getRule().trim().substring(0,preNode.getRule().trim().length()-1));
        nextNode.setRule(nextNode.getRule().trim().substring(1));
    }

    private static boolean isClose(Node currentNode) {
        Node preNode = currentNode.getPreNode();
        Node nextNode = currentNode.getNextNode();
        if (preNode.getRule().trim().endsWith("((") && nextNode.getRule().trim().startsWith("))")) {
            return true;
        }
        return false;
    }

    private static boolean isAnd(Node currentNode) {
        String currentValue = currentNode.getRule();
        String lowerCase = currentValue.toLowerCase();
        return lowerCase.contains("and");
    }

    private static int getLength(Node firstNode) {
        int length = 0;
        while (!Objects.isNull(firstNode)) {
            length++;
            firstNode = firstNode.getNextNode();
        }
        return length;
    }

}
