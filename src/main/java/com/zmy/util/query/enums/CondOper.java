package com.zmy.util.query.enums;

/**
 * @program: sql-generate-util
 * @description: 条件操作
 * @author: zhangmy
 * @create: 2021-12-04 23:18
 */
public enum CondOper {

    GREATERTHAN(0," > ","大于"),
    GREATERTHANOREQUAL(1," >= ","大于等于"),
    LESSTHAN(2," < ","小于"),
    LESSTHANOREQUAL(3," <= ","小于等于"),
    EQUAL(4," = ","等于"),
    UNEQUAL(5," != ","不等于"),
    LIKE(6," like ","类似于"),
    NOTLIKE(7," not like ","不包含"),
    ISNULL(8," is null ","为空")
    ;

    private int code;
    private String sign;
    private String description;

    CondOper(int code, String sign, String description) {
        this.code = code;
        this.sign = sign;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    public String getDescription() {
        return description;
    }

}
