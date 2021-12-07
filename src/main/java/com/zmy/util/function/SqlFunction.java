package com.zmy.util.function;

/**
 * @program: business-receipt-build-tool-parent
 * @description:
 * @author: zhangmy
 * @create: 2021-12-05 22:20
 */
public class SqlFunction {

    public String DATE_FORMAT(String column) {
        return "DATE_FORMAT(" + column + ")";
    }

}
