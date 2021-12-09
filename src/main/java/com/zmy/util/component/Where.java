package com.zmy.util.component;


import com.zmy.util.enums.AndOr;
import com.zmy.util.enums.Bracket;
import com.zmy.util.enums.ColumnOper;
import com.zmy.util.enums.CondOper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description: Where组件
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
@Data
public class Where<T> extends NodeComponent<Where<?>> implements INodeComponent<Where<?>> {
    private String table;
    private String column;
    private ColumnOper columnOper;
    private CondOper condOper;
    private T value;

    public Where(String table, String column, ColumnOper columnOper, CondOper condOper, T value) {
        this.table = table;
        this.column = column;
        this.columnOper = columnOper;
        this.condOper = condOper;
        this.value = value;
        this.brackets = new ArrayList<>();
        this.last = this;
    }

    public Where(String table, String column, CondOper condOper, T value) {
        this.table = table;
        this.column = column;
        this.columnOper = ColumnOper.NONE;
        this.condOper = condOper;
        this.value = value;
        this.brackets = new ArrayList<>();
        this.last = this;
    }

    public Where(String table, String column, CondOper condOper) {
        this.table = table;
        this.column = column;
        this.columnOper = ColumnOper.NONE;
        this.condOper = condOper;
        this.brackets = new ArrayList<>();
        this.last = this;
    }

    @Override
    public String toString() {
        return "Where{" +
                "brackets=" + brackets +
                ", table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", condOper=" + condOper +
                ", value=" + value +
                ", andOr=" + andOr +
                ", where=" + next +
                '}';
    }

}
