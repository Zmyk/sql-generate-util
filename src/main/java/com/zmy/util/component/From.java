package com.zmy.util.component;

import com.zmy.util.enums.Join;

/**
 * @program: sql-generate-util
 * @description: From组件
 * @author: zhangmy
 * @create: 2021-12-04 23:13
 */
public class From extends Component{

    private String table;
    private String tableAlias;
    private Join join;
    private On on;
    private From next;
    private From last;

    public String getTable() {
        return table;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public Join getJoin() {
        return join;
    }

    public On getOn() {
        return on;
    }

    public From getNext() {
        return next;
    }

    public From getLast() {
        return last;
    }

    private From(String table, String tableAlias) {
        this.table = table;
        this.tableAlias = tableAlias;
        last = this;
    }

    private void setTable(String table) {
        this.table = table;
    }

    private void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    private void setJoin(Join join) {
        this.join = join;
    }

    private void setOn(On on) {
        this.on = on;
    }

    private void setNext(From next) {
        this.next = next;
    }

    private void setLast(From last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "From{" +
                "table='" + table + '\'' +
                ", tableAlias='" + tableAlias + '\'' +
                ", join=" + join +
                ", on=" + on +
                ", next=" + next +
                ", last=" + last +
                '}';
    }

    public From leftJoin(From from) {
        from.setJoin(Join.LEFTJOIN);
        this.last.setNext(from);
        this.last = from;
        return this;
    }

    public From rightJoin(From from) {
        from.setJoin(Join.RIGHTJOIN);
        this.last.setNext(from);
        this.last = from;
        return this;
    }

    public From innerJoin(From from) {
        from.setJoin(Join.INNERJOIN);
        this.last.setNext(from);
        this.last = from;
        return this;
    }

    public From on(On on) {
        this.last.setOn(on);
        return this;
    }

    public static class FromBuilder extends MemberCheck<From,FromBuilder> {

        private String table;
        private String tableAlias;

        public FromBuilder table(String table) {
            this.table = table;
            return this;
        }

        public FromBuilder tableAlias(String tableAlias) {
            this.tableAlias = tableAlias;
            return this;
        }

        public static FromBuilder builder() {
            return new FromBuilder();
        }

        @Override
        protected From buildInstance() {
            return new From(table,tableAlias);
        }

    }



}
