package com.zmy.util.component;

import com.zmy.util.enums.AndOr;
import com.zmy.util.enums.Bracket;
import lombok.Data;

import java.util.List;

/**
 * @program: sql-generate-util
 * @description: NodeComponent应该具备的属性特点
 *      NodeComponent是指链式结构的组件
 * @author: zhangmy
 * @create: 2021-12-09 15:23
 */
@Data
public class NodeComponent<T> {

    protected List<Bracket> brackets;
    protected AndOr andOr;
    protected T next;
    protected T last;

}
