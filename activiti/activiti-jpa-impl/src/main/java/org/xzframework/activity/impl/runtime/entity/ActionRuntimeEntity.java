package org.xzframework.activity.impl.runtime.entity;


import jakarta.persistence.*;
import org.xzframework.activity.define.model.User;
import org.xzframework.activity.impl.embedded.AbstractEntity;
import org.xzframework.activity.impl.embedded.EmbeddedUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "xz_activiti_runtime_action_")
public class ActionRuntimeEntity extends AbstractEntity {


    @Column(name = "name_")
    private String name;


    @ManyToOne
    @JoinColumn(name = "step_id_")
    private StepRuntimeEntity step;


    @ManyToOne
    @JoinColumn(name = "next_step_id_")
    private StepRuntimeEntity nextStep;


    @ElementCollection
    @CollectionTable(name = "xz_activiti_runtime_action_user_")
    @AttributeOverrides({
            @AttributeOverride(name = "id_", column = @Column(name = "user_id_")),
            @AttributeOverride(name = "name", column = @Column(name = "user_name_", length = 50))
    })
    private List<EmbeddedUser> users = new ArrayList<>();


    public List<User> getUsers() {
        return List.copyOf(users);
    }

    public void setUsers(List<EmbeddedUser> users) {
        this.users.clear();
        if (Objects.nonNull(users)) {
            this.users.addAll(users);
        }
    }

    public StepRuntimeEntity getStep() {
        return step;
    }

    public Optional<StepRuntimeEntity> getNextStep() {
        return Optional.ofNullable(nextStep);
    }

    public String getName() {
        return name;
    }
}
