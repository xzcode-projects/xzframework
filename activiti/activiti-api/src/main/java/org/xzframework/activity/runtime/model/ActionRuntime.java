package org.xzframework.activity.runtime.model;

import java.util.Optional;

/**
 * 一个操作信息
 */
public interface ActionRuntime {

    Long getId();

    /**
     * 动作
     */
    String getName();

    /**
     * 动作的下一部
     */
    Optional<StepRuntime> getNextStep();


    ProcessRuntime getProcess();

    StepRuntime getStep();
}
