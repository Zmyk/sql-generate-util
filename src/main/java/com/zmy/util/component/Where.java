package com.zmy.util.component;


import com.zmy.util.enums.ColumnOper;
import com.zmy.util.enums.CondOper;

import java.util.ArrayList;

/**
 * @program: sql-generate-util
 * @description: Where组件
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
public class Where extends NodeComponent<Where> {
    private String table;
    private String column;
    private ColumnOper columnOper;
    private CondOper condOper;
    private Object value;

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public ColumnOper getColumnOper() {
        return columnOper;
    }

    public CondOper getCondOper() {
        return condOper;
    }

    public Object getValue() {
        return value;
    }

    private Where(String table, String column, ColumnOper columnOper, CondOper condOper, Object value) {
        this.table = table;
        this.column = column;
        this.columnOper = columnOper;
        this.condOper = condOper;
        this.value = value;
        this.brackets = new ArrayList<>();
        this.last = this;
    }

    @Override
    public String toString() {
        return "Where{" +
                "table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", columnOper=" + columnOper +
                ", condOper=" + condOper +
                ", value=" + value +
                ", brackets=" + brackets +
                ", andOr=" + andOr +
                ", next=" + next +
                ", last=" + last +
                '}';
    }

    public static class WhereBuilder extends MemberCheck<Where,WhereBuilder> {

        private String table;
        private String column;
        private ColumnOper columnOper = ColumnOper.NONE;;
        private CondOper condOper;
        private Object value;

        public WhereBuilder table(String table) {
            this.table = table;
            return this;
        }
        public WhereBuilder column(String column) {
            this.column = column;
            return this;
        }
        public WhereBuilder columnOper(ColumnOper columnOper) {
            this.columnOper = columnOper;
            return this;
        }
        public WhereBuilder condOper(CondOper condOper) {
            this.condOper = condOper;
            return this;
        }
        public WhereBuilder value(Object value) {
            this.value = value;
            return this;
        }

        public static WhereBuilder builder() {
            return new WhereBuilder();
        }

        @Override
        protected Where buildInstance() {
            return new Where(table, column, columnOper, condOper, value);
        }
    }


}
