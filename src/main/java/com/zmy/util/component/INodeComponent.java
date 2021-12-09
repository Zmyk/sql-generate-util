package com.zmy.util.component;

import com.zmy.util.enums.AndOr;
import com.zmy.util.enums.Bracket;

import java.util.List;
import java.util.Objects;

/**
 * @program: sql-generate-util
 * @description:
 *      该类是将链式结构的组件提供的方法提取出来
 *      使用泛型，实现子类在继承了default方法后能够得到处理正确类型的方法。
 * @author: zhangmy
 * @create: 2021-12-08 11:30
 */
public interface INodeComponent<T extends NodeComponent<T>> {

    /**
     * 接口泛型的类型检查，避免做强制类型转换时得到错误的结果
     * @param t
     * @throws Exception
     */
    private void typeCheck(T t) throws Exception {
        Class<? extends INodeComponent> aClass = this.getClass();
        Class<? extends NodeComponent> aClass1 = t.getClass();
        if (!aClass.equals(aClass1)) {
            throw new Exception("INodeComponent接口不能这样用，接口泛型应使用子类本身！但是[" + aClass1.toString()
                    + "]在定义时却使用了类型[" + aClass1.toString());
        }
    }

    /**
     * 此方法为：返回下一个节点的方法
     * @return
     */
    T getNext();

    /**
     * 设置下一个节点
     * @param t
     */
    void setNext(T t);

    /**
     * 设置连接条件
     * @param andOr
     */
    void setAndOr(AndOr andOr);

    /**
     * 获取当前链表的最后一个节点
     * @return
     */
    T getLast();

    /**
     * 设置当前链表的最后一个节点
     * @param t
     */
    void setLast(T t);

    /**
     * 设置括号
     * @return
     */
    List<Bracket> getBrackets();

    /**
     * 获取括号
     * @param brackets
     */
    void setBrackets(List<Bracket> brackets);


    //start----继承给子类向外提供的方法
    /**
     * 按照and方式做连接操作
     * @param t
     * @return
     */
    default T and(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.connect(t,AndOr.AND);
        }
        return (T) this;
    }

    /**
     * 按照and方式做连接操作，参数组件部分作为一个整体加括号
     * @param t
     * @return
     */
    default T andPart(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.connectPart(t,AndOr.AND);
        }
        return (T) this;
    }

    /**
     * 按照and方式做连接操作，调用方组件部分作为一个整体加括号
     * @param t
     * @return
     */
    default T partAnd(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.partConnect(t,AndOr.AND);
        }
        return (T) this;
    }

    /**
     * 按照and方式做连接操作，调用方和参数组件部分作为一个整体加括号
     * @param t
     * @return
     */
    default T partAndPart(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.partConnectPart(t,AndOr.AND);
        }
        return (T) this;
    }

    /**
     * 按照or方式做连接操作
     * @param t
     * @return
     */
    default T or(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.connect(t, AndOr.OR);
        }
        return (T) this;
    }

    /**
     * 按照or方式做连接操作，参数组件部分作为一个整体加括号
     * @param t
     * @return
     */
    default T orPart(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.connectPart(t,AndOr.OR);
        }
        return (T) this;
    }

    /**
     * 按照or方式做连接操作，调用方组件部分作为一个整体加括号
     * @param t
     * @return
     */
    default T partOr(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.partConnect(t,AndOr.OR);
        }
        return (T) this;
    }

    /**
     * 按照or方式做连接操作，调用方和参数组件部分作为一个整体加括号
     * @param t
     * @return
     */
    default T partOrPart(T t) throws Exception {
        if (!Objects.isNull(t)) {
            this.partConnectPart(t,AndOr.OR);
        }
        return (T) this;
    }
    //end----继承给子类向外提供的方法

    /**
     * 1、连接节点  2、调整对象内部结构：设置连接方式、记录最后一个节点
     * @param t andOr
     * @return
     */
    private T connect(T t,AndOr andOr) throws Exception {
        if (!Objects.isNull(t)) {
            t.setAndOr(andOr);
            this.getLast().setNext(t);
            this.setLast(this.getTheLastNode(t));
        }
        return (T) this;
    }

    /**
     * 调用方作为一个整体做连接，1、节点连接  2、调用方加括号
     * @param t
     * @return
     */
    private T partConnect(T t,AndOr andOr) throws Exception {
        if (!Objects.isNull(t)) {
            this.addPreBracket();
            this.connect(t, andOr);
        }
        return (T) this;
    }

    /**
     * 参数作为一个整体做连接，1、节点连接  2、参数加括号
     * @param t
     * @return
     */
    private T connectPart(T t,AndOr andOr) throws Exception {
        if (!Objects.isNull(t)) {
            this.addNextBracket(t);
            this.connect(t, andOr);
        }
        return (T) this;
    }

    /**
     * 调用方和参数作为两个整体做连接，1、节点连接  2、调用方和参数加括号
     * @param t
     * @return
     */
    private T partConnectPart(T t,AndOr andOr) throws Exception {
        if (!Objects.isNull(t)) {
            this.addPreBracket();
            this.addNextBracket(t);
            this.connect(t,andOr);
        }
        return (T) this;
    }

    /**
     * this加整体括号。
     * 需要注意：一般情况下要在做连接之前调用该方法，
     *          连接过后this结构发生改变，再调用该方法可能会出现意想不到的结果
     * @throws Exception
     */
    private void addPreBracket() throws Exception {
        this.getBrackets().add(Bracket.LEFTBRACKET);
        this.getTheLastNode((T)this).getBrackets().add(Bracket.RIGHTBRACKET);
    }

    /**
     * 参数方加整体括号
     * @param t
     * @throws Exception
     */
    private void addNextBracket(T t) throws Exception {
        t.getBrackets().add(Bracket.LEFTBRACKET);
        this.getTheLastNode(t).getBrackets().add(Bracket.RIGHTBRACKET);
    }

    /**
     * 获取指定类型、指定链表的最后一个节点。
     * 每次做组件操作都会调用该方法，故在此方法中做接口泛型的类型检查
     * @param t
     * @return
     * @throws Exception
     */
    private T getTheLastNode(T t) throws Exception {
        typeCheck(t);
        while (!Objects.isNull(t)) {
            if (Objects.isNull(t.getNext())) {
                break;
            }
            t = t.getNext();
        }
        return t;
    }
}
