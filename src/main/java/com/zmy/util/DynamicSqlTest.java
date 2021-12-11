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
import java.util.Date;
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
        QuerySql querySql = new QuerySql();
        querySql.setSelects(
                Arrays.asList(
                        Select.SelectBuilder.builder().table("user").column("name").alias("用户名").build(),
                        Select.SelectBuilder.builder().table("user").column("salary").alias("薪资").columnOper(ColumnOper.SUM).build(),
                        Select.SelectBuilder.builder().table("department").column("name").alias("部门").build(),
                        Select.SelectBuilder.builder().table("role").column("name").alias("角色").build()
                )
        );
        //构造from
        querySql.setFrom(
                From.FromBuilder.builder().table("user").tableAlias("u").build().leftJoin(
                        From.FromBuilder.builder().table("department").tableAlias("d1").build().on(
                                On.OnBuilder.builder().table1("u").column1("id").condOper(CondOper.EQUAL).table2("d1").column2("user_id").build()
                                        .or(On.OnBuilder.builder().table1("u").column1("name").condOper(CondOper.EQUAL).table2("d1").column2("user_name").build())
                                        .andPart(On.OnBuilder.builder().table1("u").column1("col1").condOper(CondOper.EQUAL).table2("d1").column2("col2").build())
                        )
                ).leftJoin(From.FromBuilder.builder().table("role").tableAlias("r1").build().on(On.OnBuilder.builder().table1("u").column1("id").condOper(CondOper.EQUAL).table2("r1").column2("user_id").build()))
        );
        //构造where
        querySql.setWhere(
                Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").condOper(CondOper.GREATERTHANOREQUAL).value(new Date()).build())
       );
        //构造group by
        querySql.setGroupBys(
                Arrays.asList(
                        GroupBy.GroupByBuilder.builder().table("user").column("id").build(),
                        GroupBy.GroupByBuilder.builder().table("role").column("id").build()
                )
        );
        //构造having
        querySql.setHaving(
                Having.HavingBuilder.builder().table("user").column("age").condOper(CondOper.GREATERTHAN).value(20).build()
                .and(Having.HavingBuilder.builder().table("user").column("gender").condOper(CondOper.EQUAL).value("女").build())

        );
        //构造order by
        querySql.setOrderBys(
                Arrays.asList(
                        OrderBy.OrderByBuilder.builder().table("user").column("id").order(Order.ASC).build(),
                        OrderBy.OrderByBuilder.builder().table("department").column("name").order(Order.ASC).build(),
                        OrderBy.OrderByBuilder.builder().table("role").column("id").order(Order.DESC).build()
                )
        );
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(querySql));
    }

    @org.junit.Test
    public void test02() throws Exception {
        RuleNode ruleNode = new RuleNode("((0) or (1)) and ((2) or (3) or (4))");
        HashMap<String,Where> map = new HashMap<>();
        map.put("0", Where.WhereBuilder.builder().table("0").column("0").condOper(CondOper.EQUAL).value("0").build());
        map.put("1", Where.WhereBuilder.builder().table("1").column("1").condOper(CondOper.EQUAL).value("1").build());
        map.put("2", Where.WhereBuilder.builder().table("2").column("2").condOper(CondOper.EQUAL).value("2").build());
        map.put("3", Where.WhereBuilder.builder().table("3").column("3").condOper(CondOper.EQUAL).value("3").build());
        map.put("4", Where.WhereBuilder.builder().table("4").column("4").condOper(CondOper.EQUAL).value("4").build());
        Where where = RuleUtil.handler(ruleNode, map);
        QuerySql querySql = new QuerySql();
        querySql.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(querySql));

    }

    @org.junit.Test
    public void test03() throws Exception {
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").columnOper(ColumnOper.DATE_FORMAT).condOper(CondOper.GREATERTHANOREQUAL).value(new DateTime()).build());
        QuerySql querySql = new QuerySql();
        querySql.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(querySql));
    }

    @org.junit.Test
    public void test04() throws Exception {
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").condOper(CondOper.GREATERTHANOREQUAL).value(new DateTime()).build());
        QuerySql querySql = new QuerySql();
        querySql.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(querySql));
    }

    @org.junit.Test
    public void test05() throws Exception {
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").condOper(CondOper.ISNULL).build());
        QuerySql querySql = new QuerySql();
        querySql.setWhere(where);
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(querySql));
    }

}
