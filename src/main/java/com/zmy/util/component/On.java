package com.zmy.util.component;


import com.zmy.util.enums.AndOr;
import com.zmy.util.enums.Bracket;
import com.zmy.util.enums.CondOper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: business-receipt-build-tool-parent
 * @description: On结构类似于where结构，可参考where相关注释及思路
 * @author: zhangmy
 * @create: 2021-12-04 23:14
 */
@Data
public class On {

    private List<Bracket> brackets;
    private String table1;
    private String column1;
    private CondOper condOper;
    private String table2;
    private String column2;
    private AndOr andOr;
    private On on;

    private On last;

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
                ", on=" + on +
                '}';
    }

    public On and(On on) {
        if (!Objects.isNull(on)) {
            this.connect(on,AndOr.AND);
        }
        return this;
    }

    public On andPart(On on) {
        if (!Objects.isNull(on)) {
            this.connectPart(on,AndOr.AND);
        }
        return this;
    }

    public On partAnd(On on) {
        if (!Objects.isNull(on)) {
            this.partConnect(on,AndOr.AND);
        }
        return this;
    }

    public On partAndPart(On on) {
        if (!Objects.isNull(on)) {
            this.partConnectPart(on,AndOr.AND);
        }
        return this;
    }

    public On or(On on) {
        if (!Objects.isNull(on)) {
            this.connect(on,AndOr.OR);
        }
        return this;
    }

    public On orPart(On on) {
        if (!Objects.isNull(on)) {
            this.connectPart(on,AndOr.OR);
        }
        return this;
    }

    public On partOr(On on) {
        if (!Objects.isNull(on)) {
            this.partConnect(on,AndOr.OR);
        }
        return this;
    }

    public On partOrPart(On on) {
        if (!Objects.isNull(on)) {
            this.partConnectPart(on,AndOr.OR);
        }
        return this;
    }

    private On getLast(On on) {
        while (!Objects.isNull(on)) {
            if (!Objects.isNull(on.getOn())) {
                on = on.getOn();
            } else {
                break;
            }
        }
        return on;
    }

    private On partConnect(On on,AndOr andOr) {
        if (!Objects.isNull(on)) {
            this.addPreBracket();
            this.connect(on,andOr);
        }
        return this;
    }

    private On connectPart(On on,AndOr andOr) {
        if (!Objects.isNull(on)) {
            this.addNextBracket(on);
            this.connect(on,andOr);
        }
        return this;
    }

    private On partConnectPart(On on,AndOr andOr) {
        if (!Objects.isNull(on)) {
            this.addPreBracket();
            this.addNextBracket(on);
            this.connect(on,andOr);
        }
        return this;
    }

    private On connect(On on, AndOr andOr) {
        if (!Objects.isNull(on)) {
            on.setAndOr(andOr);
            this.last.setOn(on);
            this.last = this.getLast(on);
        }
        return this;
    }

    private void addPreBracket() {
        this.getBrackets().add(Bracket.LEFTBRACKET);
        this.getLast(this).getBrackets().add(Bracket.RIGHTBRACKET);
    }

    private void addNextBracket(On on) {
        on.getBrackets().add(Bracket.LEFTBRACKET);
        this.getLast(on).getBrackets().add(Bracket.RIGHTBRACKET);
    }

}
