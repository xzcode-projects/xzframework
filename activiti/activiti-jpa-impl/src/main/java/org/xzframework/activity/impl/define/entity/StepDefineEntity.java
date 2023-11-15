package org.xzframework.activity.impl.define.entity;

import jakarta.persistence.*;

/**
 * 流程步骤
 */
@Entity
@Table(name = "xz_activiti_step_define_")
public class StepDefineEntity {

    /**
     * 步骤的ID
     */
    @Id
    @Column(name = "id_")
    private Long id;

    /**
     * 步骤的名称
     */
    @Column(name = "name_", length = 200)
    private String name;

    /**
     * 步骤关联的流程
     */
    @ManyToOne
    @JoinColumn(name = "process_id_")
    private ProcessDefineEntity process;
}
