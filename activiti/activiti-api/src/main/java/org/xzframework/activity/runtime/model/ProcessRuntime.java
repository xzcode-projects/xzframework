package org.xzframework.activity.runtime.model;

import java.util.List;

/**
 * 一个流程实例
 */
public interface ProcessRuntime {
    Long getId();

    String getName();

    /**
     * 获取流程的所有步骤
     */
    List<StepRuntime> getSteps();

    /**
     * 获取流程的当前步骤
     */
    StepRuntime getCurrentStep();
}
