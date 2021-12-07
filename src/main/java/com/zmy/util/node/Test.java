package com.zmy.util.node;

import com.zmy.util.DynamicSqlParam;
import com.zmy.util.DynamicSqlUtil;
import com.zmy.util.component.Where;
import com.zmy.util.enums.CondOper;

import java.util.HashMap;

/**
 * @program: sql-generate-util
 * @description:
 * @author: zhangmy
 * @create: 2021-12-07 16:02
 */
public class Test {

    @org.junit.Test
    public void test01() {
        Node node = new Node("((0) or (1)) and ((2) or (3) or (4))");
        HashMap<String,Where<Object>> map = new HashMap<>();
        map.put("0", new Where<Object>("0", "0", CondOper.EQUAL, 0));
        map.put("1", new Where<Object>("1", "1", CondOper.EQUAL, 1));
        map.put("2", new Where<Object>("2", "2", CondOper.EQUAL, 2));
        map.put("3", new Where<Object>("3", "3", CondOper.EQUAL, 3));
        map.put("4", new Where<Object>("4", "4", CondOper.EQUAL, 4));
        Where<Object> where = RuleUtil.handler(node, map);
        DynamicSqlParam dynamicSqlParam = new DynamicSqlParam();
        dynamicSqlParam.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(dynamicSqlParam));

    }

}
