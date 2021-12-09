/**
 * @program: sql-generate-util
 *
 * @description:
 *  * 该类定义了一个where类型的构造器，支持高级的where构造方式，按照指定规则将where构造出来，map的键是占位符的对应数字
 *  * 例如：
 *  *         RuleNode ruleNode = new RuleNode("((0) or (1)) and ((2) or (3) or (4))");
 *  *         HashMap<String,Where<Object>> map = new HashMap<>();
 *  *         map.put("0", new Where<Object>("0", "0", CondOper.EQUAL, 0));
 *  *         map.put("1", new Where<Object>("1", "1", CondOper.EQUAL, 1));
 *  *         map.put("2", new Where<Object>("2", "2", CondOper.EQUAL, 2));
 *  *         map.put("3", new Where<Object>("3", "3", CondOper.EQUAL, 3));
 *  *         map.put("4", new Where<Object>("4", "4", CondOper.EQUAL, 4));
 *  *         Where<Object> where = RuleUtil.handler(ruleNode, map);
 *
 * @author: zhangmy
 * @create: 2021-12-09 16:34
 */
package com.zmy.util.node;
