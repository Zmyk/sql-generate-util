package com.zmy.util.enums;

/**
 * @program: sql-generate-util
 * @description: 表连接方式
 * @author: zhangmy
 * @create: 2021-12-04 23:18
 */
public enum Join {

    LEFTJOIN(0,"LEFT JOIN","左连接"),
    RIGHTJOIN(1,"RIGHT JOIN","右连接"),
    INNERJOIN(2,"INNER JOIN","内连接")
    ;

    private int code;
    private String sign;
    private String description;

    Join(int code, String sign, String description) {
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
