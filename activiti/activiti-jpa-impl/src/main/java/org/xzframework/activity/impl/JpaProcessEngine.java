package org.xzframework.activity.impl;

import org.springframework.transaction.annotation.Transactional;
import org.xzframework.activity.define.model.User;
import org.xzframework.activity.impl.runtime.entity.ActionRuntimeEntity;
import org.xzframework.activity.impl.runtime.entity.ExecuteHistoryEntity;
import org.xzframework.activity.impl.runtime.entity.ProcessRuntimeEntity;
import org.xzframework.activity.impl.runtime.entity.StepRuntimeEntity;
import org.xzframework.activity.impl.runtime.model.ActionRuntimeModel;
import org.xzframework.activity.impl.runtime.model.ExecuteHistoryModel;
import org.xzframework.activity.impl.runtime.model.ProcessRuntimeModel;
import org.xzframework.activity.impl.runtime.model.StepRuntimeModel;
import org.xzframework.activity.impl.runtime.repository.ExecuteHistoryRepository;
import org.xzframework.activity.impl.runtime.repository.ProcessRuntimeRepository;
import org.xzframework.activity.runtime.model.ActionRuntime;
import org.xzframework.activity.runtime.model.ExecuteHistory;
import org.xzframework.activity.runtime.model.ProcessRuntime;
import org.xzframework.activity.runtime.model.StepRuntime;
import org.xzframework.activity.service.ProcessEngine;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class JpaProcessEngine implements ProcessEngine {

    private final ProcessRuntimeRepository processRepository;

    private final ExecuteHistoryRepository historyRepository;

    public JpaProcessEngine(ProcessRuntimeRepository processRepository, ExecuteHistoryRepository historyRepository) {
        this.processRepository = processRepository;
        this.historyRepository = historyRepository;
    }


    @Override
    @Transactional
    public void execute(ProcessRuntime process, User user, ActionRuntime action) {
        Long processId = process.getId();
        ProcessRuntimeEntity existProcess = processRepository.getReferenceById(processId);
        List<ActionRuntimeEntity> executeAbleActions = existProcess.getCurrentStep().getActions();
        Optional<ActionRuntimeEntity> executeAction = executeAbleActions.stream().filter(it -> it.getId().equals(action.getId())).findFirst();
        Optional<User> executeUser = executeAction.map(ActionRuntimeEntity::getUsers).stream().flatMap(Collection::stream).filter(it -> it.getId().equals(user.getId())).findFirst();
        if (executeAction.isEmpty()) {
            throw new RuntimeException("不正确的流程动作");
        }
        if (executeUser.isEmpty()) {
            throw new RuntimeException("流程发起人员不匹配");
        }
        Optional<StepRuntimeEntity> nextStep = executeAction.get().getNextStep();
        if (nextStep.isEmpty()) {
            // TODO 如果没有下一步。代表这个流程已经终结了
        } else {
            // 如果流程动作有下一部，将
            existProcess.setCurrentStep(nextStep.get());
        }
    }

    /**
     * 查询流程的执行历史记录
     *
     * @param process
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExecuteHistory> listExectueHistory(ProcessRuntime process) {
        Long processId = process.getId();
        List<ExecuteHistoryEntity> historyEntities = historyRepository.findByProcessId(processId);
        return historyEntities.stream().map(this::toModel).toList();

    }


    private ExecuteHistory toModel(ExecuteHistoryEntity entity) {
        return new ExecuteHistoryModel(
                entity.getId(),
                entity.getExecutor(),
                entity.getExecuteTime(),
                toModel(entity.getAction())
        );
    }


    private ActionRuntime toModel(ActionRuntimeEntity entity) {
        return new ActionRuntimeModel(
                entity.getId(),
                entity.getName(),
                toModel(entity.getStep().getProcess()),
                toModel(entity.getStep()),
                entity.getNextStep().map(this::toModel).orElse(null)
        );
    }

    private ProcessRuntime toModel(ProcessRuntimeEntity entity) {
        return new ProcessRuntimeModel(entity.getId(),
                entity.getName(),
                Collections.emptyList(),
                toModel(entity.getCurrentStep())
        );
    }

    private StepRuntime toModel(StepRuntimeEntity entity) {
        return new StepRuntimeModel(
                entity.getId(),
                entity.getName()
        );
    }
}
