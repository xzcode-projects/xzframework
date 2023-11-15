package org.xzframework.activity.impl.runtime.model;

import org.xzframework.activity.runtime.model.ActionRuntime;
import org.xzframework.activity.runtime.model.ProcessRuntime;
import org.xzframework.activity.runtime.model.StepRuntime;

import java.util.Optional;

public class ActionRuntimeModel implements ActionRuntime {


    private final Long id;
    private final String name;
    private final StepRuntime nextStep;
    private final ProcessRuntime process;
    private final StepRuntime step;

    public ActionRuntimeModel(Long id,
                              String name,
                              ProcessRuntime process,
                              StepRuntime step,
                              StepRuntime nextStep
    ) {
        this.id = id;
        this.name = name;
        this.nextStep = nextStep;
        this.process = process;
        this.step = step;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<StepRuntime> getNextStep() {
        return Optional.ofNullable(nextStep);
    }

    @Override
    public ProcessRuntime getProcess() {
        return process;
    }

    @Override
    public StepRuntime getStep() {
        return step;
    }

}
