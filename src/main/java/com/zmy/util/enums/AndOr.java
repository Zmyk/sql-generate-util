package com.zmy.util.enums;

/**
 * @program: sql-generate-util
 * @description: 条件连接方式
 * @author: zhangmy
 * @create: 2021-12-04 23:16
 */
public enum AndOr {

    AND(0,"AND","and连接符号"),
    OR(1,"OR","or连接符号")
    ;

    private int code;
    private String sign;
    private String description;

    AndOr(int code, String sign, String description) {
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
