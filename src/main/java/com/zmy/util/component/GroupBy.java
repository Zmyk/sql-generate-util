package com.zmy.util.component;

/**
 * @program: business-receipt-build-tool-parent
 * @description:
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
public class GroupBy {

    private String table;
    private String column;

    public GroupBy() {
    }

    public GroupBy(String table, String column) {
        this.table = table;
        this.column = column;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "GroupBy{" +
                "table='" + table + '\'' +
                ", column='" + column + '\'' +
                '}';
    }

}
