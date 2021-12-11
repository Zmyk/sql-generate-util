package com.zmy.util.component;

/**
 * @program: sql-generate-util
 * @description:
 * @author: zhangmy
 * @create: 2021-12-11 23:02
 */
public abstract class MemberCheck<T,E> implements ComponentBuilder<T,E> {


    @Override
    public T build() {
        if (!isCheckOk()) {
            throw new RuntimeException("On参数检查不合法！");
        }
        return buildInstance();
    }

    /**
     * 成员合法性检查
     * @return
     */
    protected boolean isCheckOk(){
        return true;
    }

    /**
     * 每个组件返回各自的实例的接口。参考了lombok的@Builder方式，在基础上做了一些个性化操作
     * @return 返回实际组件类型的实列
     */
    protected abstract T buildInstance();

}
