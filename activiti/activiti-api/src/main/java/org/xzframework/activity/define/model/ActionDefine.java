package org.xzframework.activity.define.model;

import java.util.List;

/**
 * 表示动作定义
 *
 * @author ldwqh0@outlook.com
 */
public interface ActionDefine {

    /**
     * 动作
     */
    String getName();

    /**
     * 动作的下一部
     */
    StepDefine getNextStep();


    ProcessDefinition getProcessDefine();

    StepDefine getStep();

    // 指定可以执行该动作的人员
    List<User> getPrincipal();
}
