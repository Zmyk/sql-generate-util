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
 * @program: business-receipt-build-tool-parent
 * @description:
 * @author: zhangmy
 * @create: 2021-12-04 23:15
 */
@Data
public class Having<T> implements IComponentNode<Having<?>> {

    private List<Bracket> brackets;
    private String table;
    private String column;
    private ColumnOper columnOper;
    private CondOper condOper;
    private T value;
    private AndOr andOr;
    private Having<?> next;

    private Having<?> last;


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

    public Having<T> and(Having<?> having){
        if (!Objects.isNull(having)) {
            this.connect(having,AndOr.AND);
        }
        return this;
    }

    public Having<T> andPart(Having<?> having) {
        if (!Objects.isNull(having)) {
            this.connectPart(having,AndOr.AND);
        }
        return this;
    }

    public Having<T> partAnd(Having<?> having) {
        if (!Objects.isNull(having)) {
            this.partConnect(having,AndOr.AND);
        }
        return this;
    }

    public Having<T> partAndPart(Having<?> having) {
        if (!Objects.isNull(having)) {
            this.partConnectPart(having,AndOr.AND);
        }
        return this;
    }

    public Having<T> or(Having<?> having){
        if (!Objects.isNull(having)) {
            this.connect(having,AndOr.OR);
        }
        return this;
    }

    public Having<T> orPart(Having<?> having) {
        if (!Objects.isNull(having)) {
            this.connectPart(having,AndOr.OR);
        }
        return this;
    }

    public Having<T> partOr(Having<?> having) {
        if (!Objects.isNull(having)) {
            this.partConnect(having,AndOr.OR);
        }
        return this;
    }

    public Having<T> partOrPart(Having<?> having) {
        if (!Objects.isNull(having)) {
            this.partConnectPart(having,AndOr.OR);
        }
        return this;
    }

//    private Having<?> getTheLastNode(Having<?> having) {
//        while (!Objects.isNull(having)) {
//            if (!Objects.isNull(having.getNext())) {
//                having = having.getNext();
//            } else {
//                break;
//            }
//        }
//        return having;
//    }

    private Having<T> partConnect(Having<?> having,AndOr andOr) {
        if (!Objects.isNull(having)) {
            this.addPreBracket();
            this.connect(having,andOr);
        }
        return this;
    }

    private Having<T> connectPart(Having<?> having,AndOr andOr) {
        if (!Objects.isNull(having)) {
            this.addNextBracket(having);
            this.connect(having,andOr);
        }
        return this;
    }

    private Having<T> partConnectPart(Having<?> having,AndOr andOr) {
        if (!Objects.isNull(having)) {
            this.addPreBracket();
            this.addNextBracket(having);
            this.connect(having,andOr);
        }
        return this;
    }

    private Having<T> connect(Having<?> having, AndOr andOr) {
        if (!Objects.isNull(having)) {
            having.setAndOr(andOr);
            this.last.setNext(having);
            this.last = this.getTheLastNode(having);
        }
        return this;
    }

    private void addPreBracket() {
        this.getBrackets().add(Bracket.LEFTBRACKET);
        this.getTheLastNode(this).getBrackets().add(Bracket.RIGHTBRACKET);
    }

    private void addNextBracket(Having<?> having) {
        having.getBrackets().add(Bracket.LEFTBRACKET);
        this.getTheLastNode(having).getBrackets().add(Bracket.RIGHTBRACKET);
    }

}
