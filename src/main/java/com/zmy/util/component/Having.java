package com.zmy.util.component;


import com.zmy.util.enums.ColumnOper;
import com.zmy.util.enums.CondOper;

import java.util.ArrayList;

/**
 * @program: sql-generate-util
 * @description: Having组件
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
public class Having extends NodeComponent<Having> {

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

    private Having(String table, String column, ColumnOper columnOper, CondOper condOper, Object value) {
        this.brackets = new ArrayList<>();
        this.table = table;
        this.column = column;
        this.columnOper = columnOper;
        this.condOper = condOper;
        this.value = value;
        this.last = this;
    }

    @Override
    public String toString() {
        return "Having{" +
                "brackets=" + brackets +
                ", table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", columnOper=" + columnOper +
                ", condOper=" + condOper +
                ", value=" + value +
                ", andOr=" + andOr +
                ", having=" + next +
                '}';
    }

    public static class HavingBuilder extends MemberCheck<Having,HavingBuilder> {

        private String table;
        private String column;
        private ColumnOper columnOper = ColumnOper.NONE;
        private CondOper condOper;
        private Object value;

        public HavingBuilder table(String table) {
            this.table = table;
            return this;
        }
        public HavingBuilder column(String column) {
            this.column = column;
            return this;
        }
        public HavingBuilder columnOper(ColumnOper columnOper) {
            this.columnOper = columnOper;
            return this;
        }
        public HavingBuilder condOper(CondOper condOper) {
            this.condOper = condOper;
            return this;
        }
        public HavingBuilder value(Object value) {
            this.value = value;
            return this;
        }

        public static HavingBuilder builder() {
            return new HavingBuilder();
        }

        @Override
        protected Having buildInstance() {
            return new Having(table,column,columnOper,condOper,value);
        }

    }


}
