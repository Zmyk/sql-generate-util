package com.zmy.util.query.component;

import cn.hutool.core.util.StrUtil;
import com.zmy.util.query.component.builder.MemberCheck;
import com.zmy.util.query.enums.Order;

/**
 * @program: sql-generate-util
 * @description: OrderBy组件
 * @author: zhangmy
 * @create: 2021-12-04 23:16
 */
public class OrderBy extends Component{

    private String table;
    private String column;
    private Order order;

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public Order getOrder() {
        return order;
    }

    private OrderBy(String table, String column, Order order) {
        this.table = table;
        this.column = column;
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderBy{" +
                "table='" + table + '\'' +
                ", column='" + column + '\'' +
                ", order=" + order +
                '}';
    }

    public static class OrderByBuilder extends MemberCheck<OrderBy> {

        private String table;
        private String column;
        private Order order;

        public OrderByBuilder table(String table) {
            this.table = table;
            return this;
        }
        public OrderByBuilder column(String column) {
            this.column = column;
            return this;
        }
        public OrderByBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public static OrderByBuilder builder() {
            return new OrderByBuilder();
        }

        @Override
        protected OrderBy buildInstance() {
            return new OrderBy(table,column,order);
        }

        @Override
        protected boolean isCheckOk() {
            boolean isCheckOk = true;
            if (StrUtil.isEmpty(table)) {
                isCheckOk = false;
                errMsg+=("order by 条件table不能为null！");
            }
            if (StrUtil.isEmpty(column)) {
                isCheckOk = false;
                errMsg+=("order by 条件column不能为null！");
            }
            if (order == null) {
                isCheckOk = false;
                errMsg+=("order by 条件order不能为null！");
            }
            return isCheckOk;
        }
    }


}
