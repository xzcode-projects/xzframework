package org.xzframework.activity.runtime.model;

import org.xzframework.activity.define.model.User;

import java.time.ZonedDateTime;

public interface ExecuteHistory {
    /**
     * 执行时间
     */
    ZonedDateTime getExecuteTime();

    Long getId();

    /**
     * 执行人
     */
    User getExecutor();

    /**
     * 执行的动作
     */
    ActionRuntime getAction();

}
