package org.xzframework.activity.impl.runtime.model;

import org.xzframework.activity.define.model.User;
import org.xzframework.activity.runtime.model.ActionRuntime;
import org.xzframework.activity.runtime.model.ExecuteHistory;

import java.time.ZonedDateTime;

public class ExecuteHistoryModel implements ExecuteHistory {

    private final Long id;

    private final User executor;

    private final ZonedDateTime executeTime;

    private final ActionRuntime action;

    public ExecuteHistoryModel(Long id,
                               User executor,
                               ZonedDateTime executeTime,
                               ActionRuntime action
    ) {
        this.id = id;
        this.executor = executor;
        this.executeTime = executeTime;
        this.action = action;
    }


    @Override
    public ZonedDateTime getExecuteTime() {
        return executeTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public User getExecutor() {
        return executor;
    }

    @Override
    public ActionRuntime getAction() {
        return this.action;
    }
}
