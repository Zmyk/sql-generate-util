package com.zmy.util.component;


import com.zmy.util.enums.AndOr;
import com.zmy.util.enums.Bracket;
import com.zmy.util.enums.CondOper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description: On组件
 * @author: zhangmy
 * @create: 2021-12-04 23:14
 */
@Data
public class On extends NodeComponent<On> implements INodeComponent<On> {

    private String table1;
    private String column1;
    private CondOper condOper;
    private String table2;
    private String column2;

    public On(String table1, String column1, CondOper condOper, String table2, String column2) {
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

}
