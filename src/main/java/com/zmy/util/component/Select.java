package com.zmy.util.component;

import com.zmy.util.enums.ColumnOper;

/**
 * @program: business-receipt-build-tool-parent
 * @description:
 * @author: zhangmy
 * @create: 2021-12-04 23:13
 */
public class Select {

    private String table;
    private String column;
    private String alias;
    private ColumnOper columnOper;

    public Select() {
    }

    public Select(String table, String column, String alias, ColumnOper columnOper) {
        this.table = table;
        this.column = column;
        this.alias = alias;
        this.columnOper = columnOper;
    }

    public Select(String table, String column, String alias) {
        this.table = table;
        this.column = column;
        this.alias = alias;
        this.columnOper = ColumnOper.NONE;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public String getAlias() {
        return alias;
    }

    public ColumnOper getColumnOper() {
        return columnOper;
    }

    @Override
    public String toString() {
        return "Select{" +
                "table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", alias='" + alias + '\'' +
                ", columnOper=" + columnOper +
                '}';
    }

}
