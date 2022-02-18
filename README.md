# sql-generate-util

#### 介绍
通过构造对象的方式，生成sql。

#### 使用说明
拉取项目源码，使用maven将jar安装到本地仓库。在pom文件中引入gav，即可使用。

使用示例
```java
public class QuerySqlTest {

    @Test
    public void test01() throws Exception {
        List<Select> selects = Arrays.asList(
                Select.SelectBuilder.builder().table("user").column("name").alias("用户名").build(),
                Select.SelectBuilder.builder().table("user").column("salary").alias("薪资").columnOper(ColumnOper.SUM).build(),
                Select.SelectBuilder.builder().table("department").column("name").alias("部门").build(),
                Select.SelectBuilder.builder().table("role").column("name").alias("角色").build()
        );
        //构造from
        From from = From.FromBuilder.builder().table("user").tableAlias("u").build().leftJoin(
                From.FromBuilder.builder().table("department").tableAlias("d1").build().on(
                        On.OnBuilder.builder().table1("u").column1("id").condOper(CondOper.EQUAL).table2("d1").column2("user_id").build()
                                .or(On.OnBuilder.builder().table1("u").column1("name").condOper(CondOper.EQUAL).table2("d1").column2("user_name").build())
                                .andPart(On.OnBuilder.builder().table1("u").column1("col1").condOper(CondOper.EQUAL).table2("d1").column2("col2").build())
                )
        ).leftJoin(From.FromBuilder.builder().table("role").tableAlias("r1").build().on(On.OnBuilder.builder().table1("u").column1("id").condOper(CondOper.EQUAL).table2("r1").column2("user_id").build()));
        //构造where
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").condOper(CondOper.GREATERTHANOREQUAL).value(new Date()).build());
        //构造group by

        List<GroupBy> groupBys = Arrays.asList(
                GroupBy.GroupByBuilder.builder().table("user").column("id").build(),
                GroupBy.GroupByBuilder.builder().table("role").column("id").build()
        );
        //构造having
        Having having = Having.HavingBuilder.builder().table("user").column("age").condOper(CondOper.GREATERTHAN).value(20).build()
                .and(Having.HavingBuilder.builder().table("user").column("gender").condOper(CondOper.EQUAL).value("女").build())
                .partOr(Having.HavingBuilder.builder().table("user").column("gg").condOper(CondOper.ISNULL).build());
        //构造order by
        List<OrderBy> orderBys = Arrays.asList(
                OrderBy.OrderByBuilder.builder().table("user").column("id").order(Order.ASC).build(),
                OrderBy.OrderByBuilder.builder().table("department").column("name").order(Order.ASC).build(),
                OrderBy.OrderByBuilder.builder().table("role").column("id").order(Order.DESC).build()
        );
        QuerySql build = QuerySql.QuerySqlBuilder.builder().selects(selects).from(from).where(where).groupBys(groupBys).having(having).orderBys(orderBys).build();
        System.out.println(QuerySqlParser.builder().parse(build));
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
        From from = From.FromBuilder.builder().table("s").build();
        QuerySql build = QuerySql.QuerySqlBuilder.builder().from(from).where(where).build();
        System.out.println(QuerySqlParser.builder().parse(build));

    }

    @org.junit.Test
    public void test03() throws Exception {
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").columnOper(ColumnOper.DATE_FORMAT).condOper(CondOper.GREATERTHANOREQUAL).value(new DateTime()).build());
        From from = From.FromBuilder.builder().table("s").build();
        QuerySql build = QuerySql.QuerySqlBuilder.builder().from(from).where(where).build();
        System.out.println(QuerySqlParser.builder().parse(build));
    }

    @org.junit.Test
    public void test04() throws Exception {
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").condOper(CondOper.GREATERTHANOREQUAL).value(new DateTime()).build());
        From from = From.FromBuilder.builder().table("s").build();
        QuerySql build = QuerySql.QuerySqlBuilder.builder().from(from).where(where).build();
        System.out.println(QuerySqlParser.builder().parse(build));
    }

    @org.junit.Test
    public void test05() throws Exception {
        Where where = Where.WhereBuilder.builder().table("user").column("id").condOper(CondOper.EQUAL).value(123456789).build()
                .or(Where.WhereBuilder.builder().table("department").column("id").condOper(CondOper.EQUAL).value("de456852").build())
                .partAnd(Where.WhereBuilder.builder().table("user").column("create_time").condOper(CondOper.ISNULL).build());
        From from = From.FromBuilder.builder().table("s").build();
        QuerySql build = QuerySql.QuerySqlBuilder.builder().from(from).where(where).build();
        System.out.println(QuerySqlParser.builder().parse(build));
    }

}
```
