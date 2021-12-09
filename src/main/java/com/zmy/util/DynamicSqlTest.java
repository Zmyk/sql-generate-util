package com.zmy.util;

import cn.hutool.core.date.DateTime;
import com.zmy.util.component.*;
import com.zmy.util.enums.ColumnOper;
import com.zmy.util.enums.CondOper;
import com.zmy.util.enums.Order;
import com.zmy.util.node.RuleNode;
import com.zmy.util.node.RuleUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @program: sql-generate-util
 * @description: 开发者测试了一下功能。
 * @author: zhangmy
 * @create: 2021-12-04 23:19
 */
public class DynamicSqlTest {

    @Test
    public void test01() throws Exception {
        DynamicSqlParam param = new DynamicSqlParam();
        //构造select
        param.setSelects(
                Arrays.asList(new Select("user","name","用户名", ColumnOper.NONE),
                        new Select("user","salary","薪资", ColumnOper.SUM),
                        new Select("department","name","部门", ColumnOper.NONE),
                        new Select("role","name","角色", ColumnOper.NONE)));
        //构造from
        param.setFrom(
                new From("user").as("u").leftJoin("department").as("d1").on(
                                                                new On("u","id", CondOper.EQUAL,"d1","user_id").or(new On("u","name",CondOper.EQUAL,"d1","user_name"))
                                                                .andPart(new On("u","col1",CondOper.EQUAL,"d1","col2")))
                        .leftJoin("role").as("r1").on(new On("u","id", CondOper.EQUAL,"r1","user_id"))
        );

        //构造where
        param.setWhere(
                new Where<Object>("user","id",CondOper.EQUAL,123456798)
                        .or(new Where("department","id",CondOper.EQUAL,"de456852")).partAnd(new Where("user","create_time",CondOper.GREATERTHANOREQUAL,new DateTime()))
        );
        //构造group by
        param.setGroupBys(
                Arrays.asList(
                        new GroupBy("user","id"),
                        new GroupBy("role","id")
                )
        );
        //构造having
        param.setHaving(
                new Having<Object>("user","age", ColumnOper.NONE, CondOper.GREATERTHAN,20)
                        .and(new Having("user","gender",ColumnOper.NONE,CondOper.EQUAL,"女"))
        );
        //构造order by
        param.setOrderBys(
                Arrays.asList(
                        new OrderBy("user","id", Order.ASC),
                        new OrderBy("department","name",Order.ASC),
                        new OrderBy("role","id",Order.DESC)
                )
        );
//        System.out.println(JSON.toJSONString(param));
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(param));
    }

    @org.junit.Test
    public void test02() throws Exception {
//        List<Object> list = new ArrayList<Integer>();
        RuleNode ruleNode = new RuleNode("((0) or (1)) and ((2) or (3) or (4))");
        HashMap<String,Where<Object>> map = new HashMap<>();
        map.put("0", new Where<Object>("0", "0", CondOper.EQUAL, 0));
        map.put("1", new Where<Object>("1", "1", CondOper.EQUAL, 1));
        map.put("2", new Where<Object>("2", "2", CondOper.EQUAL, 2));
        map.put("3", new Where<Object>("3", "3", CondOper.EQUAL, 3));
        map.put("4", new Where<Object>("4", "4", CondOper.EQUAL, 4));
        Where<Object> where = RuleUtil.handler(ruleNode, map);
        DynamicSqlParam dynamicSqlParam = new DynamicSqlParam();
        dynamicSqlParam.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(dynamicSqlParam));

    }

    @org.junit.Test
    public void test03() throws Exception {
        Where<?> where = new Where<Object>("user", "id", CondOper.EQUAL, 123456798)
                .or(new Where<Object>("department", "id", CondOper.EQUAL, "de456852"))
                .partAnd(new Where<Object>("user", "create_time",ColumnOper.DATE_FORMAT, CondOper.GREATERTHANOREQUAL ,new DateTime()));
        DynamicSqlParam dynamicSqlParam = new DynamicSqlParam();
        dynamicSqlParam.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(dynamicSqlParam));
    }

    @org.junit.Test
    public void test04() throws Exception {
        Where<?> where = new Where<Object>("user", "id", CondOper.EQUAL, 123456798)
                .or(new Where<Object>("department", "id", CondOper.EQUAL, "de456852"))
                .partAnd(new Where<Object>("user", "create_time", CondOper.GREATERTHANOREQUAL, new DateTime()));
        DynamicSqlParam dynamicSqlParam = new DynamicSqlParam();
        dynamicSqlParam.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(dynamicSqlParam));
    }

    @org.junit.Test
    public void test05() throws Exception {
        Where<?> where = new Where<Object>("user", "id", CondOper.EQUAL, 123456798)
                .or(new Where<Object>("department", "id", CondOper.EQUAL, "de456852"))
                .partAnd(new Where<Object>("user", "create_time",CondOper.ISNULL));
        DynamicSqlParam dynamicSqlParam = new DynamicSqlParam();
        dynamicSqlParam.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(dynamicSqlParam));
    }

}
