package com.zmy.util.query.component.builder;

/**
 * @program: sql-generate-util
 * @description:
 * @author: zhangmy
 * @create: 2021-12-11 21:43
 */
public interface ComponentBuilder<T> {

    /**
     * 构建实例
     * @return
     */
    T build();

}
