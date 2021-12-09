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
 * @description: Having组件
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
@Data
public class Having<T> extends NodeComponent<Having<?>> implements INodeComponent<Having<?>> {

    private String table;
    private String column;
    private ColumnOper columnOper;
    private CondOper condOper;
    private T value;


    public Having(String table, String column, ColumnOper columnOper, CondOper condOper, T value) {
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

}
