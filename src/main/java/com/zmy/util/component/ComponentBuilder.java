package com.zmy.util.component;

/**
 * @program: sql-generate-util
 * @description:
 * @author: zhangmy
 * @create: 2021-12-11 21:43
 */
public interface ComponentBuilder<T,E> {

    /**
     * 构建实例
     * @return
     */
    T build();

}
