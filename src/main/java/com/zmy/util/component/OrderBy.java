package com.zmy.util.component;

import com.zmy.util.enums.Order;
import lombok.Data;

/**
 * @program: sql-generate-util
 * @description: OrderBy组件
 * @author: zhangmy
 * @create: 2021-12-04 23:16
 */
@Data
public class OrderBy {

    private String table;
    private String column;
    private Order order;

    public OrderBy() {
    }

    public OrderBy(String table, String column, Order order) {
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

}
