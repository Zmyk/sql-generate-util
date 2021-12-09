package com.zmy.util.component;

import com.zmy.util.enums.Join;
import lombok.Data;

/**
 * @program: sql-generate-util
 * @description: From组件
 * @author: zhangmy
 * @create: 2021-12-04 23:13
 */
@Data
public class From {

    private String table;
    private String tableAlias;
    private Join join;
    private On on;
    private From from;
    private From last;

    public From(String table) {
        this.table = table;
        last = this;
    }

    private From(String table, Join join) {
        this.table = table;
        this.join = join;
    }

    @Override
    public String toString() {
        return "From{" +
                "table='" + table + '\'' +
                ", join=" + join +
                ", on=" + on +
                ", from=" + from +
                '}';
    }

    public From as(String tableAlias) {
        this.last.setTableAlias(tableAlias);
        return this;
    }

    public From leftJoin(String table) {
        From newFrom = new From(table, Join.LEFTJOIN);
        this.last.setFrom(newFrom);
        this.last = newFrom;
        return this;
    }

    public From rightJoin(String table) {
        From newFrom = new From(table, Join.RIGHTJOIN);
        this.last.setFrom(newFrom);
        this.last = newFrom;
        return this;
    }

    public From innerJoin(String table) {
        From newFrom = new From(table, Join.INNERJOIN);
        this.last.setFrom(newFrom);
        this.last = newFrom;
        return this;
    }

    public From on(On on) {
        this.last.setOn(on);
        return this;
    }

}
