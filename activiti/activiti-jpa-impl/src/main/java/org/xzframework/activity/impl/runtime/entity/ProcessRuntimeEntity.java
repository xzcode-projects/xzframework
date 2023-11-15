package org.xzframework.activity.impl.runtime.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.xzframework.activity.impl.embedded.AbstractEntity;

@Entity
@Table(name = "xz_activiti_runtime_process_")
public class ProcessRuntimeEntity extends AbstractEntity {


    private String name;

    @OneToOne
    @JoinColumn(name = "current_step_id_")
    private StepRuntimeEntity currentStep;


    public String getName() {
        return name;
    }

    public StepRuntimeEntity getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(StepRuntimeEntity currentStep) {
        this.currentStep = currentStep;
    }
}
