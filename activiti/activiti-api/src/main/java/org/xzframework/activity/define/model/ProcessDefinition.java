package org.xzframework.activity.define.model;

import java.util.List;

/**
 * 流程定义
 *
 * @author ldwqh0@outlook.com
 */
public interface ProcessDefinition {


    /**
     * 获取流程定义名称
     */
    String getName();

    /**
     * 获取流程节点
     */
    List<StepDefine> getSteps();

}
