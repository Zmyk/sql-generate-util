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
public class Where<T> {

    private List<Bracket> brackets;
    private String table;
    private String column;
    private ColumnOper columnOper;
    private CondOper condOper;
    private T value;
    private AndOr andOr;
    private Where<T> where;

    private Where<T> last;

    public Where(String table, String column, CondOper condOper,ColumnOper columnOper, T value) {
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
                ", where=" + where +
                '}';
    }

    /**
     * 按照and方式做连接操作
     * @param where
     * @return
     */
    public Where<T> and(Where<T> where){
        if (!Objects.isNull(where)) {
            this.connect(where,AndOr.AND);
        }
        return this;
    }

    /**
     * 按照and方式做连接操作，参数where作为一个整体加括号
     * @param where
     * @return
     */
    public Where<T> andPart(Where<T> where){
        if (!Objects.isNull(where)) {
            this.connectPart(where,AndOr.AND);
        }
        return this;
    }

    /**
     * 按照and方式做连接操作，调用方where作为一个整体加括号
     * @param where
     * @return
     */
    public Where<T> partAnd(Where<T> where){
        if (!Objects.isNull(where)) {
            this.partConnect(where,AndOr.AND);
        }
        return this;
    }

    /**
     * 按照and方式做连接操作，调用方和参数where作为一个整体加括号
     * @param where
     * @return
     */
    public Where<T> partAndPart(Where<T> where){
        if (!Objects.isNull(where)) {
            this.partConnectPart(where,AndOr.AND);
        }
        return this;
    }

    /**
     * 按照or方式做连接操作
     * @param where
     * @return
     */
    public Where<T> or(Where<T> where){
        if (!Objects.isNull(where)) {
            this.connect(where,AndOr.OR);
        }
        return this;
    }

    /**
     * 按照or方式做连接操作，参数where作为一个整体加括号
     * @param where
     * @return
     */
    public Where<T> orPart(Where<T> where){
        if (!Objects.isNull(where)) {
            this.connectPart(where,AndOr.OR);
        }
        return this;
    }

    /**
     * 按照or方式做连接操作，调用方where作为一个整体加括号
     * @param where
     * @return
     */
    public Where<T> partOr(Where<T> where){
        if (!Objects.isNull(where)) {
            this.partConnect(where,AndOr.OR);
        }
        return this;
    }

    /**
     * 按照or方式做连接操作，调用方和参数where作为一个整体加括号
     * @param where
     * @return
     */
    public Where<T> partOrPart(Where<T> where){
        if (!Objects.isNull(where)) {
            this.partConnectPart(where,AndOr.OR);
        }
        return this;
    }

    private Where<T> getLast(Where<T> where) {
        while (!Objects.isNull(where)) {
            if (!Objects.isNull(where.getWhere())) {
                where = where.getWhere();
            } else {
                break;
            }
        }
        return where;
    }

    /**
     * 调用方作为一个整体做连接，1、节点连接  2、调用方加括号
     * @param where
     * @return
     */
    private Where<T> partConnect(Where<T> where,AndOr andOr) {
        if (!Objects.isNull(where)) {
            this.addPreBracket();
            this.connect(where, andOr);
        }
        return this;
    }

    /**
     * 参数作为一个整体做连接，1、节点连接  2、参数加括号
     * @param where
     * @return
     */
    private Where<T> connectPart(Where<T> where,AndOr andOr) {
        if (!Objects.isNull(where)) {
            this.addNextBracket(where);
            this.connect(where, andOr);
        }
        return this;
    }

    /**
     * 调用方和参数作为两个整体做连接，1、节点连接  2、调用方和参数加括号
     * @param where
     * @return
     */
    private Where<T> partConnectPart(Where<T> where,AndOr andOr) {
        if (!Objects.isNull(where)) {
            this.addPreBracket();
            this.addNextBracket(where);
            this.connect(where,andOr);
        }
        return this;
    }

    /**
     * 1、连接节点  2、调整对象内部结构：设置连接方式、记录最后一个节点
     * @param where
     * @return
     */
    private Where<T> connect(Where<T> where,AndOr andOr) {
        if (!Objects.isNull(where)) {
            where.setAndOr(andOr);
            this.last.setWhere(where);
            this.last = this.getLast(where);
        }
        return this;
    }

    /**
     * this加整体括号。
     * 需要注意：一般情况下要在做连接之前调用该方法
     *          连接过后this结构发生改变，再调用该方法可能会出现意想不到的结果
     */
    private void addPreBracket() {
        this.getBrackets().add(Bracket.LEFTBRACKET);
        this.getLast(this).getBrackets().add(Bracket.RIGHTBRACKET);
    }

    /**
     * 参数方加整体括号
     * @param where
     */
    private void addNextBracket(Where<T> where) {
        where.getBrackets().add(Bracket.LEFTBRACKET);
        this.getLast(where).getBrackets().add(Bracket.RIGHTBRACKET);
    }

}
