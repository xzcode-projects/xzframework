package org.xzframework.activity.service;

import org.xzframework.activity.define.model.User;
import org.xzframework.activity.runtime.model.ActionRuntime;
import org.xzframework.activity.runtime.model.ExecuteHistory;
import org.xzframework.activity.runtime.model.ProcessRuntime;

import java.util.List;

/**
 * 流程引擎
 *
 * @author ldwqh0@outlook.com
 */
public interface ProcessEngine {

    /**
     * 执行一个动作
     *
     * @param user   执行动作的用户
     * @param action 要执行的动作
     */
    void execute(ProcessRuntime process, User user, ActionRuntime action);

    List<ExecuteHistory> listExectueHistory(ProcessRuntime process);
}
