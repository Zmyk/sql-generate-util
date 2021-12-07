package com.zmy.util;

import com.zmy.util.component.*;

import java.util.List;

/**
 * @program: business-receipt-build-tool-parent
 * @description:
 * @author: zhangmy
 * @create: 2021-12-04 23:12
 */
public class DynamicSqlParam {

    private List<Select> selects;

    private From from;

    private Where where;

    private List<GroupBy> groupBys;

    private Having having;

    private List<OrderBy> orderBys;

    public List<Select> getSelects() {
        return selects;
    }

    public void setSelects(List<Select> selects) {
        this.selects = selects;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public List<GroupBy> getGroupBys() {
        return groupBys;
    }

    public void setGroupBys(List<GroupBy> groupBys) {
        this.groupBys = groupBys;
    }

    public Having getHaving() {
        return having;
    }

    public void setHaving(Having having) {
        this.having = having;
    }

    public List<OrderBy> getOrderBys() {
        return orderBys;
    }

    public void setOrderBys(List<OrderBy> orderBys) {
        this.orderBys = orderBys;
    }

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
}
