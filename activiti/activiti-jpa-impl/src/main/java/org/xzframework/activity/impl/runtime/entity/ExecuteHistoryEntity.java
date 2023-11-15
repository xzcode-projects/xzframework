package org.xzframework.activity.impl.runtime.entity;

import jakarta.persistence.*;
import org.xzframework.activity.impl.embedded.AbstractEntity;
import org.xzframework.activity.impl.embedded.EmbeddedUser;

import java.time.ZonedDateTime;

/**
 * 动作执行历史
 */
@Entity
@Table(name = "xz_activiti_runtime_history_")
public class ExecuteHistoryEntity extends AbstractEntity {


    /**
     * 执行人
     */
    private EmbeddedUser executor;


    /**
     * 执行的动作
     */
    @ManyToOne
    @JoinColumn(name = "action_id_")
    private ActionRuntimeEntity action;

    /**
     * 执行时间
     */
    @Column(name = "execute_time_")
    private ZonedDateTime executeTime;

    public ActionRuntimeEntity getAction() {
        return action;
    }

    public ZonedDateTime getExecuteTime() {
        return executeTime;
    }

    /**
     * @return
     */
    public ProcessRuntimeEntity getProcess() {
        return action.getStep().getProcess();
    }

    public EmbeddedUser getExecutor() {
        return executor;
    }
}
