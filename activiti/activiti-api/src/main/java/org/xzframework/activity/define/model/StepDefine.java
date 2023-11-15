package org.xzframework.activity.define.model;

import java.util.List;

/**
 * 表示流程节点
 *
 * @author ldwqh0@outlook.com
 */
public interface StepDefine {

    /**
     * 获取节点的名称
     */
    String getName();

    List<ActionDefine> getActions();
}
