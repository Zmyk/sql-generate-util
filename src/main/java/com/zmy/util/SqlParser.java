package com.zmy.util;

/**
 * @program: sql-generate-util
 * @description:
 * @author: zhangmy
 * @create: 2021-12-13 13:57
 */
public abstract class SqlParser<T extends Sql> {

    /**
     * @Description: 解析动态sql参数
     * @Param: [param]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    public abstract String parse(T param);

    /**
     * @Description: 添加漂
     * @Param: [string]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/29
     */
    protected static String addFullwidth(String string) {
        return "`" + string + "`";
    }

    /**
     * @Description: 添加单引号
     * @Param: [string]
     * @return: java.lang.String
     * @Author: zhangmy
     * @Date: 2020/12/30
     */
    protected static String addSingleQuotes(String string) {
        return "'" + string + "'";
    }

}
