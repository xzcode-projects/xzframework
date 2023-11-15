package org.xzframework.activity.impl.runtime.entity;

import jakarta.persistence.*;
import org.xzframework.activity.impl.embedded.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "xz_activiti_runtime_step_")
public class StepRuntimeEntity extends AbstractEntity {


    @ManyToOne(optional = false)
    @JoinColumn(name = "process_id_")
    private ProcessRuntimeEntity process;

    @Column(name = "name_", length = 200)
    private String name;


    @OneToMany(mappedBy = "step")
    private List<ActionRuntimeEntity> actions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public ProcessRuntimeEntity getProcess() {
        return process;
    }

    public List<ActionRuntimeEntity> getActions() {
        return List.copyOf(actions);
    }
}
