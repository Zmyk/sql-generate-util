package com.zmy.util.component;

import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description:
 * @author: zhangmy
 * @create: 2021-12-08 11:30
 */
public interface IComponentNode<T extends IComponentNode<T>> {

    /**
     * 此方法为：返回下一个节点的方法
     * @return
     */
    T getNext();

    default T getTheLastNode(T t) {
        while (!Objects.isNull(t)) {
            if (!Objects.isNull(t.getNext())) {
                t = t.getNext();
            } else {
                break;
            }
        }
        return t;
    }
}
