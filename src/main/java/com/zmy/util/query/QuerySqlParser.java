package com.zmy.util.query;



import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zmy.util.SqlKeyWord;
import com.zmy.util.SqlParser;
import com.zmy.util.query.component.*;
import com.zmy.util.query.enums.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description: 该类是解析Sql的工具类
 * @author: zhangmy
 * @create: 2021-12-04 23:11
 */
public class QuerySqlParser extends SqlParser<QuerySql> {

    public static QuerySqlParser builder() {
        return new QuerySqlParser();
    }

    public String parse(QuerySql param) {
        //select
        StringBuilder result = new StringBuilder();
        List<Select> selects = param.getSelects();
        result.append(handlerSelects(selects));
        //from
        From from = param.getFrom();
        result.append(handlerFrom(from));
        //where
        Where where = param.getWhere();
        result.append(handlerWhere(where));
        //group by
        List<GroupBy> groupBys = param.getGroupBys();
        result.append(handlerGroupBys(groupBys));

        //having
        Having having = param.getHaving();
        result.append(handlerHaving(having));

        //order by
        List<OrderBy> orderBys = param.getOrderBys();
        result.append(handlerOrderBys(orderBys));

        return new String(result);
    }

    /**
     * @Description: 拼接select部分
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerSelects(List<Select> selects){
        if (CollectionUtil.isEmpty(selects)) {
            return SqlKeyWord.SELECT + SqlKeyWord.STAR;
        }
        StringBuilder result = new StringBuilder(SqlKeyWord.SELECT);
        for (int i = 0;i < selects.size();i++) {
            Select select = selects.get(i);
            String table = select.getTable();
            String column = select.getColumn();
            String alias = select.getAlias();
            ColumnOper columnOper = select.getColumnOper();
            result.append(handlerColumnOperFormat(table,column,columnOper))
                    .append(SqlKeyWord.AS)
                    .append(addFullwidth(alias));
            if (i != selects.size() - 1) {
                result.append(SqlKeyWord.COMMA);
            }
        }
        return new String(result);
    }

    /**
     * @Description: 解析from部分
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerFrom(From from) {
        if (Objects.isNull(from)) {
            throw new IllegalArgumentException("QuerySql的From部分不能为null");
        }
        StringBuilder result = new StringBuilder(SqlKeyWord.FROM);
        while (!Objects.isNull(from)) {
            String table = from.getTable();
            Join join = from.getJoin();
            On on = from.getOn();
            if (!Objects.isNull(join)) {
                result.append(join.getSign());
            }
            result.append(addFullwidth(table));
            if (StrUtil.isNotBlank(from.getTableAlias())) {
                result.append(SqlKeyWord.AS).append(from.getTableAlias());
            }
            if (!Objects.isNull(on)) {
                result.append(SqlKeyWord.ON);
            }
            while (!Objects.isNull(on)) {
                List<Bracket> brackets = on.getBrackets();
                AndOr andOr = on.getAndOr();
                String table1 = on.getTable1();
                String column1 = on.getColumn1();
                CondOper condOper = on.getCondOper();
                String table2 = on.getTable2();
                String column2 = on.getColumn2();
                if (!Objects.isNull(andOr)) {
                    result.append(andOr.getSign());
                }
                if (!CollectionUtil.isEmpty(brackets)) {
                    for (Bracket bracket : brackets) {
                        if (bracket.getCode() == Bracket.LEFTBRACKET.getCode()) {
                            result.append(bracket.getSign());
                        }
                    }
                }
                result.append(addFullwidth(table1))
                        .append(SqlKeyWord.POINT)
                        .append(addFullwidth(column1))
                        .append(condOper.getSign())
                        .append(addFullwidth(table2))
                        .append(SqlKeyWord.POINT)
                        .append(addFullwidth(column2));
                if (!CollectionUtil.isEmpty(brackets)) {
                    for (Bracket bracket : brackets) {
                        if (bracket.getCode() == Bracket.RIGHTBRACKET.getCode()) {
                            result.append(bracket.getSign());
                        }
                    }
                }
                on = on.getNext();
            }
            from = from.getNext();
        }
        return new String(result);
    }

    /**
     * @Description: 解析where部分
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerWhere(Where where) {
        if (Objects.isNull(where)) {
            return StrUtil.EMPTY;
        }
        StringBuilder result = new StringBuilder(SqlKeyWord.WHERE);
        while (!Objects.isNull(where)) {
            List<Bracket> brackets = where.getBrackets();
            String table = where.getTable();
            String column = where.getColumn();
            ColumnOper columnOper = where.getColumnOper();
            CondOper condOper = where.getCondOper();
            Object value = where.getValue();
            AndOr andOr = where.getAndOr();
            if (!Objects.isNull(andOr)) {
                result.append(andOr.getSign());
            }
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.LEFTBRACKET.getCode()) {
                        result.append(bracket.getSign());
                    }
                }
            }
            result.append(handlerColumnOperFormat(table,column,columnOper))
                    .append(condOper.getSign())
                    .append(handlerValueType(handlerValueFormat(condOper,value)));
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.RIGHTBRACKET.getCode()) {
                        result.append(bracket.getSign());
                    }
                }
            }
            where = where.getNext();
        }
        return new String(result);
    }

    /**
     * @Description: 处理不同的聚合操作表现。比如：date_format，需要处理成 date_format(table.column,'%Y-%m-%d')
     * @Author: zhangmy
     * @Date: 2021/1/21 14:45
     */
    private static String handlerColumnOperFormat(String table,String column,ColumnOper columnOper) {
        if (ColumnOper.DATE_FORMAT.equals(columnOper)) {
            return "DATE_FORMAT(" + addFullwidth(table) + SqlKeyWord.POINT + addFullwidth(column) + ",'%Y-%m-%d')";
        }
        if (ColumnOper.NONE.equals(columnOper)) {
            return addFullwidth(table) + SqlKeyWord.POINT + addFullwidth(column);
        }
        return columnOper.getSign() + "(" + addFullwidth(table) +SqlKeyWord.POINT+ addFullwidth(column) + ")";
    }

    /**
     * @Description: 处理不同的操作符号时，值的表现。比如：like，需要在value前后加 ‘%’
     * @Author: zhangmy
     * @Date: 2021/1/21 14:45
     */
    private static Object handlerValueFormat(CondOper condOper,Object value) {
        if (CondOper.LIKE.equals(condOper)) {
            return "%" + value + "%";
        }
        if (CondOper.ISNULL.equals(condOper)) {
            return null;
        }
        return value;
    }

    /**
     * @Description: 解析group by部分
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerGroupBys(List<GroupBy> groupBys) {
        if (CollectionUtil.isEmpty(groupBys)) {
            return StrUtil.EMPTY;
        }
        StringBuilder result = new StringBuilder(SqlKeyWord.GROUP_BY);
        for (int i = 0; i < groupBys.size(); i++) {
            GroupBy groupBy = groupBys.get(i);
            String table = groupBy.getTable();
            String column = groupBy.getColumn();
            result.append(addFullwidth(table))
                    .append(SqlKeyWord.POINT)
                    .append(addFullwidth(column));
            if (i != groupBys.size() - 1) {
                result.append(SqlKeyWord.COMMA);
            }
        }
        return new String(result);
    }

    /**
     * @Description: 解析having部分
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerHaving(Having having) {
        if (Objects.isNull(having)) {
            return StrUtil.EMPTY;
        }
        StringBuilder result = new StringBuilder(SqlKeyWord.HAVING);
        while (!Objects.isNull(having)) {
            List<Bracket> brackets = having.getBrackets();
            String table = having.getTable();
            String column = having.getColumn();
            ColumnOper columnOper = having.getColumnOper();
            CondOper condOper = having.getCondOper();
            Object value = having.getValue();
            AndOr andOr = having.getAndOr();
            if (!Objects.isNull(andOr)) {
                result.append(andOr.getSign());
            }
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.LEFTBRACKET.getCode()) {
                        result.append(bracket.getSign());
                    }
                }
            }
            result.append(handlerColumnOperFormat(table,column,columnOper))
                    .append(condOper.getSign())
                    .append(handlerValueType(handlerValueFormat(condOper,value)));
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.RIGHTBRACKET.getCode()) {
                        result.append(bracket.getSign());
                    }
                }
            }
            having = having.getNext();
        }
        return new String(result);
    }

    /**
     * @Description: 解析order by部分
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerOrderBys(List<OrderBy> orderBys) {
        if (CollectionUtil.isEmpty(orderBys)) {
            return StrUtil.EMPTY;
        }
        StringBuilder result = new StringBuilder(SqlKeyWord.ORDER_BY);
        for (int i = 0; i < orderBys.size(); i++) {
            OrderBy orderBy = orderBys.get(i);
            String table = orderBy.getTable();
            String column = orderBy.getColumn();
            Order order = orderBy.getOrder();
            result.append(addFullwidth(table))
                    .append(SqlKeyWord.POINT)
                    .append(addFullwidth(column))
                    .append(order.getSign());
            if (i != orderBys.size() - 1) {
                result.append(SqlKeyWord.COMMA);
            }
        }
        return new String(result);
    }

    /**
     * @Description: 处理值类型表现
     * @Author: zhangmy
     * @Date: 2020/12/30
     */
    private static <T> String handlerValueType(T value) {
        if (Objects.isNull(value)) {
            return "";
        }
        if (value instanceof String) {
            return addSingleQuotes((String) value);
        }
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof Date) {
            return addSingleQuotes(value.toString());
        }
        return value.toString();
    }

}
