package org.xzframework.activity.impl.runtime.model;

import org.xzframework.activity.runtime.model.ProcessRuntime;
import org.xzframework.activity.runtime.model.StepRuntime;

import java.util.List;

public class ProcessRuntimeModel implements ProcessRuntime {

    private final Long id;

    private final String name;

    private final List<StepRuntime> steps;

    private final StepRuntime currentStep;

    public ProcessRuntimeModel(
            Long id,
            String name,
            List<StepRuntime> steps,
            StepRuntime currentStep
    ) {
        this.id = id;
        this.name = name;
        this.steps = List.copyOf(steps);
        this.currentStep = currentStep;
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
    public List<StepRuntime> getSteps() {
        return List.copyOf(steps);
    }

    @Override
    public StepRuntime getCurrentStep() {
        return currentStep;
    }
}
