package com.zmy.util.enums;

/**
 * @program: sql-generate-util
 * @description: 字段操作
 * @author: zhangmy
 * @create: 2021-12-04 23:17
 */
public enum ColumnOper {

    NONE(0,"NONE","取值不聚合"),
    SUM(1,"SUM","求和"),
    AVG(2,"AVG","求平均值"),
    MAX(3,"MAX","最大值"),
    MIN(4,"MIN","最小值"),
    COUNT(5,"COUNT","统计"),
    DATE_FORMAT(6,"DATE_FORMAT","日期格式化，固定格式化为 %Y-%m-%d 的格式");

    private int code;
    private String sign;
    private String description;

    ColumnOper(int code, String sign, String description) {
        this.code = code;
        this.sign = sign;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
