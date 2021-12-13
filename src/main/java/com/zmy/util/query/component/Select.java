package com.zmy.util.query.component;

import cn.hutool.core.util.StrUtil;
import com.zmy.util.query.component.builder.MemberCheck;
import com.zmy.util.query.enums.ColumnOper;

import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description: Select组件
 * @author: zhangmy
 * @create: 2021-12-04 23:13
 */
public class Select extends Component{

    private String table;
    private String column;
    private String alias;
    private ColumnOper columnOper;

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

    private Select(String table, String column, String alias, ColumnOper columnOper) {
        this.table = table;
        this.column = column;
        this.alias = alias;
        this.columnOper = columnOper;
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

    public static class SelectBuilder extends MemberCheck<Select> {

        private String table;
        private String column;
        private String alias;
        private ColumnOper columnOper = ColumnOper.NONE;

        public SelectBuilder table(String table){
            this.table = table;
            return this;
        }

        public SelectBuilder column(String column) {
            this.column = column;
            return this;
        }

        public SelectBuilder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public SelectBuilder columnOper(ColumnOper columnOper) {
            this.columnOper = columnOper;
            return this;
        }

        public static SelectBuilder builder() {
            return new SelectBuilder();
        }

        @Override
        protected Select buildInstance() {
            return new Select(table,column,alias,columnOper);
        }

        @Override
        protected boolean isCheckOk() {
            boolean isCheckOK = true;
            if (StrUtil.isEmpty(table)) {
                isCheckOK = false;
                this.errMsg += "select 条件的table不能为空！";
            }
            if (StrUtil.isEmpty(column)) {
                isCheckOK = false;
                this.errMsg += "select 条件的column不能为空！";
            }
            if (StrUtil.isEmpty(alias)) {
                isCheckOK = false;
                this.errMsg += "select 条件的alias不能为空！";
            }
            if (Objects.isNull(columnOper)) {
                isCheckOK = false;
                this.errMsg += "select 条件的columnOper不能为空！";
            }
            return isCheckOK;
        }
    }

}
