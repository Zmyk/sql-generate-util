package com.zmy.util;



import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zmy.util.component.*;
import com.zmy.util.enums.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description: 该类是解析DynamicSqlParam的工具类
 * @author: zhangmy
 * @create: 2021-12-04 23:11
 */
public class DynamicSqlUtil {

    /**
     * @Description: 解析动态sql参数
     * @Param: [param]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    public static String parseDynamicSqlParam(DynamicSqlParam param) {
        //select
        StringBuilder result = new StringBuilder("select ");
        List<Select> selects = param.getSelects();
        result.append(handlerSelects(selects));
        //from
        result.append("from ");
        From from = param.getFrom();
        result.append(handlerFrom(from));
        //where
        Where<?> where = param.getWhere();
        if (where != null) {
            result.append("where ")
                    .append(handlerWhere(where));
        }
        //group by
        List<GroupBy> groupBys = param.getGroupBys();
        if (!CollectionUtil.isEmpty(groupBys)) {
            result.append("GROUP BY ")
                    .append(handlerGroupbys(groupBys));
        }
        //having
        Having having = param.getHaving();
        if (having != null) {
            result.append("HAVING ")
                    .append(handlerHaving(having));
        }
        //order by
        List<OrderBy> orderBys = param.getOrderBys();
        if (!CollectionUtil.isEmpty(orderBys)) {
            result.append("ORDER BY ")
                    .append(handlerOrderbys(orderBys));
        }
        return new String(result);
    }

    /**
     * @Description: 拼接select部分
     * @Param: [selects]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerSelects(List<Select> selects){
        if (CollectionUtil.isEmpty(selects)) {
            return " * ";
        }
        StringBuilder result = new StringBuilder("");
        for (int i = 0;i < selects.size();i++) {
            Select select = selects.get(i);
            String table = select.getTable();
            String column = select.getColumn();
            String alias = select.getAlias();
            ColumnOper columnOper = select.getColumnOper();
            if (StrUtil.isEmpty(table)) {
                throw new IllegalArgumentException("select 条件的table不能为空！");
            }
            if (StrUtil.isEmpty(column)) {
                throw new IllegalArgumentException("select 条件的column不能为空！");
            }
            if (StrUtil.isEmpty(alias)) {
                throw new IllegalArgumentException("select 条件的alias不能为空！");
            }
            if (columnOper == null) {
                throw new IllegalArgumentException("select 条件的columnOper不能为空！");
            }
            result.append(handlerColumnOperFormat(table,column,columnOper))
                    .append(" as ")
                    .append(addFullwidth(alias));
            if (i != selects.size() - 1) {
                result.append(",");
            }else {
                result.append(" ");
            }

        }
        return new String(result);
    }

    /**
     * @Description: 解析from部分
     * @Param: [froms]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerFrom(From from) {
        if (Objects.isNull(from)) {
            return " 黑人问号 ";
        }
        StringBuilder result = new StringBuilder("");
        while (from != null) {
            String table = from.getTable();
            if (StrUtil.isEmpty(table)) {
                throw new IllegalArgumentException("from 条件的table不能为null！");
            }
            Join join = from.getJoin();
            On on = from.getOn();
            if (join != null) {
                result.append(" ").append(join.getSign()).append(" ");
            }
            result.append(addFullwidth(table)).append(" ");
            if (StrUtil.isNotBlank(from.getTableAlias())) {
                result.append(" as ").append(from.getTableAlias());
            }
            if (on != null) {
                result.append(" on ");
            }
            while (on != null) {
                List<Bracket> brackets = on.getBrackets();
                AndOr andOr = on.getAndOr();
                String table1 = on.getTable1();
                String column1 = on.getColumn1();
                CondOper condOper = on.getCondOper();
                String table2 = on.getTable2();
                String column2 = on.getColumn2();
                if (StrUtil.isEmpty(table1)) {
                    throw new IllegalArgumentException("on 条件table1不能为null");
                }
                if (StrUtil.isEmpty(column1)) {
                    throw new IllegalArgumentException("on 条件column1不能为null");
                }
                if (condOper == null) {
                    throw new IllegalArgumentException("on 条件condOper不能为null");
                }
                if (StrUtil.isEmpty(table2)) {
                    throw new IllegalArgumentException("on 条件table2不能为null");
                }
                if (StrUtil.isEmpty(column2)) {
                    throw new IllegalArgumentException("on 条件column2不能为null");
                }
                if (andOr != null) {
                    result.append(andOr.getSign()).append(" ");
                }
                if (!CollectionUtil.isEmpty(brackets)) {
                    for (Bracket bracket : brackets) {
                        if (bracket.getCode() == Bracket.LEFTBRACKET.getCode()) {
                            result.append(bracket.getSign())
                                    .append(" ");
                        }
                    }
                }
                result.append(addFullwidth(table1))
                        .append(".")
                        .append(addFullwidth(column1))
                        .append(condOper.getSign())
                        .append(addFullwidth(table2))
                        .append(".")
                        .append(addFullwidth(column2))
                        .append(" ");
                if (!CollectionUtil.isEmpty(brackets)) {
                    for (Bracket bracket : brackets) {
                        if (bracket.getCode() == Bracket.RIGHTBRACKET.getCode()) {
                            result.append(bracket.getSign())
                                    .append(" ");
                        }
                    }
                }
                on = on.getNext();
            }
            from = from.getFrom();
        }
        return new String(result);
    }

    /**
     * @Description: 解析where部分
     * @Param: [where]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerWhere(Where<?> where) {
        StringBuilder result = new StringBuilder("");
        while (where != null) {
            List<Bracket> brackets = where.getBrackets();
            String table = where.getTable();
            String column = where.getColumn();
            ColumnOper columnOper = where.getColumnOper();
            CondOper condOper = where.getCondOper();
            Object value = where.getValue();
            AndOr andOr = where.getAndOr();
            if (StrUtil.isEmpty(table)) {
                throw new IllegalArgumentException("where条件table不能为null！");
            }
            if (StrUtil.isEmpty(column)) {
                throw new IllegalArgumentException("where条件column不能为null！");
            }
            if (condOper == null) {
                throw new IllegalArgumentException("where条件condOper不能为null！");
            }
//            if (value == null) {
//                throw new IllegalArgumentException("where条件value不能为null！");
//            }
            if (!Objects.isNull(andOr)) {
                result.append(andOr.getSign()).append(" ");
            }
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.LEFTBRACKET.getCode()) {
                        result.append(bracket.getSign())
                                .append(" ");
                    }
                }
            }
            result.append(handlerColumnOperFormat(table,column,columnOper))
                    .append(" ")
                    .append(condOper.getSign())
                    .append(" ")
                    .append(handlerValueType(handlerValueFormat(condOper,value)))
                    .append(" ");
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.RIGHTBRACKET.getCode()) {
                        result.append(bracket.getSign())
                                .append(" ");
                    }
                }
            }
            where = where.getNext();
        }
        return new String(result);
    }

    /**
     * @Description: 处理不同的聚合操作表现。比如：date_format，需要处理成 date_format(table.column,'%Y-%m-%d')
     * @Param: []
     * @return: void
     * @Author: zhangmy
     * @Date: 2021/1/21 14:45
     */
    private static String handlerColumnOperFormat(String table,String column,ColumnOper columnOper) {
        if (ColumnOper.DATE_FORMAT.equals(columnOper)) {
            return "DATE_FORMAT(" + addFullwidth(table) + "." + addFullwidth(column) + ",'%Y-%m-%d')";
        }
        if (ColumnOper.NONE.equals(columnOper)) {
            return addFullwidth(table) + "." + addFullwidth(column);
        }
        return columnOper.getSign() + "(" + addFullwidth(table) +"."+ addFullwidth(column) + ")";
    }

    /**
     * @Description: 处理不同的操作符号时，值的表现。比如：like，需要在value前后加 ‘%’
     * @Param: []
     * @return: void
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
     * @Param: [groupBys]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerGroupbys(List<GroupBy> groupBys) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < groupBys.size(); i++) {
            GroupBy groupBy = groupBys.get(i);
            String table = groupBy.getTable();
            String column = groupBy.getColumn();
            result.append(addFullwidth(table))
                    .append(".")
                    .append(addFullwidth(column));
            if (i != groupBys.size() - 1) {
                result.append(",");
            }else {
                result.append(" ");
            }
        }
        return new String(result);
    }

    /**
     * @Description: 解析having部分
     * @Param: [having]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerHaving(Having<?> having) {

        StringBuilder result = new StringBuilder("");

        while (having != null) {
            List<Bracket> brackets = having.getBrackets();
            String table = having.getTable();
            String column = having.getColumn();
            ColumnOper columnOper = having.getColumnOper();
            CondOper condOper = having.getCondOper();
            Object value = having.getValue();
            AndOr andOr = having.getAndOr();
            if (StrUtil.isEmpty(table)) {
                throw new IllegalArgumentException("having条件table不能为null！");
            }
            if (StrUtil.isEmpty(column)) {
                throw new IllegalArgumentException("having条件column不能为null！");
            }
            if (columnOper == null) {
                throw new IllegalArgumentException("having条件columnOper不能为null！");
            }
            if (condOper == null) {
                throw new IllegalArgumentException("having条件condOper不能为null！");
            }
            if (value == null) {
                throw new IllegalArgumentException("having条件value不能为null！");
            }
            if (andOr != null) {
                result.append(andOr.getSign()).append(" ");
            }
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.LEFTBRACKET.getCode()) {
                        result.append(bracket.getSign())
                                .append(" ");
                    }
                }
            }
            if (columnOper.getCode() == ColumnOper.NONE.getCode()) {
                result.append(addFullwidth(table))
                        .append(".")
                        .append(addFullwidth(column))
                        .append(condOper.getSign())
                        .append(handlerValueType(value))
                        .append(" ");
            }else {
                result.append(columnOper.getSign())
                        .append("(")
                        .append(addFullwidth(table))
                        .append(".")
                        .append(addFullwidth(column))
                        .append(")")
                        .append(condOper.getSign())
                        .append(handlerValueType(value))
                        .append(" ");
            }
            if (!CollectionUtil.isEmpty(brackets)) {
                for (Bracket bracket : brackets) {
                    if (bracket.getCode() == Bracket.RIGHTBRACKET.getCode()) {
                        result.append(bracket.getSign())
                                .append(" ");
                    }
                }
            }
            having = having.getNext();
        }
        return new String(result);
    }

    /**
     * @Description: 解析order by部分
     * @Param: [orderBys]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String handlerOrderbys(List<OrderBy> orderBys) {
        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < orderBys.size(); i++) {
            OrderBy orderBy = orderBys.get(i);
            String table = orderBy.getTable();
            String column = orderBy.getColumn();
            Order order = orderBy.getOrder();
            if (StrUtil.isEmpty(table)) {
                throw new IllegalArgumentException("order by 条件table不能为null！");
            }
            if (StrUtil.isEmpty(column)) {
                throw new IllegalArgumentException("order by 条件column不能为null！");
            }
            if (order == null) {
                throw new IllegalArgumentException("order by 条件order不能为null！");
            }
            result.append(addFullwidth(table))
                    .append(".")
                    .append(addFullwidth(column))
                    .append(" ")
                    .append(order.getSign());
            if (i != orderBys.size() - 1) {
                result.append(",");
            }
        }
        return new String(result);
    }

    /**
     * @Description: 添加漂
     * @Param: [string]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    private static String addFullwidth(String string) {
        return "`" + string + "`";
    }

    /**
     * @Description: 添加单引号
     * @Param: [string]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/30
     */
    private static String addSingleQuotes(String string) {
        return "'" + string + "'";
    }

    /**
     * @Description: 处理值类型表现
     * @Param: [value]
     * @return: java.lang.String
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
