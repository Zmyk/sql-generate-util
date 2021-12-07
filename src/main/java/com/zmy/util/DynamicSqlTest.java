package com.zmy.util;

import cn.hutool.core.date.DateTime;
import com.zmy.util.component.*;
import com.zmy.util.enums.ColumnOper;
import com.zmy.util.enums.CondOper;
import com.zmy.util.enums.Order;
import org.junit.Test;

import java.util.Arrays;

/**
 * @program: business-receipt-build-tool-parent
 * @description:
 * @author: zhangmy
 * @create: 2021-12-04 23:19
 */
public class DynamicSqlTest {

    @Test
    public void test01(){
        DynamicSqlParam param = new DynamicSqlParam();
        //构造select
        param.setSelects(
                Arrays.asList(new Select("user","name","用户名", ColumnOper.NONE),
                        new Select("user","salary","薪资", ColumnOper.SUM),
                        new Select("department","name","部门", ColumnOper.NONE),
                        new Select("role","name","角色", ColumnOper.NONE)));
        //构造from   andPart orPart 调用方和参数部分整体加括号   and or  不加
        param.setFrom(
                new From("user").as("u").leftJoin("department").as("d1").on(
                                                                new On("u","id", CondOper.EQUAL,"d1","user_id").or(new On("u","name",CondOper.EQUAL,"d1","user_name"))
                                                                .andPart(new On("u","col1",CondOper.EQUAL,"d1","col2")))
                        .leftJoin("role").as("r1").on(new On("u","id", CondOper.EQUAL,"r1","user_id"))
        );

        //构造where
        param.setWhere(
                new Where<Object>("user","id",CondOper.EQUAL,123456798)
                        .or(new Where("department","id",CondOper.EQUAL,"de456852")).partAnd(new Where("user","create_time",CondOper.GREATERTHANOREQUAL,new DateTime()))
        );
        //构造group by
        param.setGroupBys(
                Arrays.asList(
                        new GroupBy("user","id"),
                        new GroupBy("role","id")
                )
        );
        //构造having
        param.setHaving(
                new Having<Object>("user","age", ColumnOper.NONE, CondOper.GREATERTHAN,20)
                        .and(new Having("user","gender",ColumnOper.NONE,CondOper.EQUAL,"女"))
        );
        //构造order by
        param.setOrderBys(
                Arrays.asList(
                        new OrderBy("user","id", Order.ASC),
                        new OrderBy("department","name",Order.ASC),
                        new OrderBy("role","id",Order.DESC)
                )
        );
//        System.out.println(JSON.toJSONString(param));
        System.out.println(DynamicSqlUtil.parseDynamicSqlParam(param));
    }

}
