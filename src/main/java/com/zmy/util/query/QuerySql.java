package com.zmy.util.query;

import com.zmy.util.Sql;
import com.zmy.util.query.component.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: sql-generate-util
 * @description:
 *      该类是最终的SQL对象
 *      在设置好组件内容后，通过DynamicSqlUtil类的parseDynamicSqlParam方法生成目标Sql字串
 * @author: zhangmy
 * @create: 2021-12-04 23:12
 */
public class QuerySql implements Sql {

    private List<Select> selects;

    private From from;

    private Where where;

    private List<GroupBy> groupBys;

    private Having having;

    private List<OrderBy> orderBys;

    public QuerySql(List<Select> selects, From from, Where where, List<GroupBy> groupBys, Having having, List<OrderBy> orderBys) {
        this.selects = selects;
        this.from = from;
        this.where = where;
        this.groupBys = groupBys;
        this.having = having;
        this.orderBys = orderBys;
    }

    //    public QuerySql select(List<Select> selects) {
//        this.selects = selects;
//
//    }

    public List<Select> getSelects() {
        return selects;
    }

//    public void setSelects(List<Select> selects) {
//        this.selects = selects;
//    }

    public From getFrom() {
        return from;
    }

//    public void setFrom(From from) {
//        this.from = from;
//    }

    public Where getWhere() {
        return where;
    }

//    public void setWhere(Where where) {
//        this.where = where;
//    }

    public List<GroupBy> getGroupBys() {
        return groupBys;
    }

//    public void setGroupBys(List<GroupBy> groupBys) {
//        this.groupBys = groupBys;
//    }

    public Having getHaving() {
        return having;
    }

//    public void setHaving(Having having) {
//        this.having = having;
//    }

    public List<OrderBy> getOrderBys() {
        return orderBys;
    }

//    public void setOrderBys(List<OrderBy> orderBys) {
//        this.orderBys = orderBys;
//    }

    @Override
    public String toString() {
        return "DynamicSqlParam{" +
                "selects=" + selects +
                ", from=" + from +
                ", where=" + where +
                ", groupBys=" + groupBys +
                ", having=" + having +
                ", orderBys=" + orderBys +
                '}';
    }

    public static class QuerySqlBuilder {
        private List<Select> selects = new ArrayList<>();

        private From from;

        private Where where;

        private List<GroupBy> groupBys = new ArrayList<>();

        private Having having;

        private List<OrderBy> orderBys = new ArrayList<>();

        public static QuerySqlBuilder builder() {
            return new QuerySqlBuilder();
        }

        public QuerySqlBuilder selects(List<Select> selects) {
            this.selects.addAll(selects);
            return this;
        }

        public QuerySqlBuilder from(From from) {
            this.from = from;
            return this;
        }

        public QuerySqlBuilder where(Where where) {
            this.where = where;
            return this;
        }

        public QuerySqlBuilder groupBys(List<GroupBy> groupBys) {
            this.groupBys.addAll(groupBys);
            return this;
        }

        public QuerySqlBuilder having(Having having) {
            this.having = having;
            return this;
        }

        public QuerySqlBuilder orderBys(List<OrderBy> orderBys) {
            this.orderBys.addAll(orderBys);
            return this;
        }

        public QuerySql build() {
            return new QuerySql(selects,from,where,groupBys,having,orderBys);
        }

    }


}
