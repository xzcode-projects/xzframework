package org.xzframework.activity.impl.define.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "xz_activiti_action_define_")
public class ActionDefineEntity {

    @Id
    @Column(name = "id_")
    private Long id;

    @Column(name = "name_", length = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "step_id_")
    private StepDefineEntity step;


//    private List<>

}
