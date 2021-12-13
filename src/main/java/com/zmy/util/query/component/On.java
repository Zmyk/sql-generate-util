package com.zmy.util.query.component;


import cn.hutool.core.util.StrUtil;
import com.zmy.util.query.component.builder.MemberCheck;
import com.zmy.util.query.enums.CondOper;

import java.util.ArrayList;

/**
 * @program: sql-generate-util
 * @description: On组件
 * @author: zhangmy
 * @create: 2021-12-04 23:14
 */
public class On extends NodeComponent<On> {

    private String table1;
    private String column1;
    private CondOper condOper;
    private String table2;
    private String column2;

    public String getTable1() {
        return table1;
    }

    public String getColumn1() {
        return column1;
    }

    public CondOper getCondOper() {
        return condOper;
    }

    public String getTable2() {
        return table2;
    }

    public String getColumn2() {
        return column2;
    }

    private On(String table1, String column1, CondOper condOper, String table2, String column2) {
        this.table1 = table1;
        this.column1 = column1;
        this.condOper = condOper;
        this.table2 = table2;
        this.column2 = column2;
        this.brackets = new ArrayList<>();
        last = this;
    }

    @Override
    public String toString() {
        return "On{" +
                "brackets=" + brackets +
                ", table1='" + table1 + '\'' +
                ", column1='" + column1 + '\'' +
                ", condOper=" + condOper +
                ", table2='" + table2 + '\'' +
                ", column2='" + column2 + '\'' +
                ", andOr=" + andOr +
                ", on=" + next +
                '}';
    }

    public static class OnBuilder extends MemberCheck<On> {

        private String table1;
        private String column1;
        private CondOper condOper;
        private String table2;
        private String column2;

        public OnBuilder table1(String table1) {
            this.table1 = table1;
            return this;
        }

        public OnBuilder column1(String column1) {
            this.column1 = column1;
            return this;
        }

        public OnBuilder condOper(CondOper condOper) {
            this.condOper = condOper;
            return this;
        }

        public OnBuilder table2(String table2) {
            this.table2 = table2;
            return this;
        }

        public OnBuilder column2(String column2) {
            this.column2 = column2;
            return this;
        }

        public static OnBuilder builder() {
            return new OnBuilder();
        }

        @Override
        protected On buildInstance() {
            return new On(table1,column1,condOper,table2,column2);
        }

        @Override
        protected boolean isCheckOk() {
            boolean isCheckOK = true;
            if (StrUtil.isEmpty(table1)) {
                isCheckOK = false;
                errMsg+=("on 条件table1不能为null");
            }
            if (StrUtil.isEmpty(column1)) {
                isCheckOK = false;
                errMsg+=("on 条件column1不能为null");
            }
            if (condOper == null) {
                isCheckOK = false;
                errMsg+=("on 条件condOper不能为null");
            }
            if (StrUtil.isEmpty(table2)) {
                isCheckOK = false;
                errMsg+=("on 条件table2不能为null");
            }
            if (StrUtil.isEmpty(column2)) {
                isCheckOK = false;
                errMsg+=("on 条件column2不能为null");
            }
            return isCheckOK;
        }
    }


}
