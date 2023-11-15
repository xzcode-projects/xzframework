package org.xzframework.activity.impl.runtime.model;

import org.xzframework.activity.runtime.model.StepRuntime;

public class StepRuntimeModel implements StepRuntime {

    private final Long id;

    private final String name;

    public StepRuntimeModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
