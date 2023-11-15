package org.xzframework.activity.impl.define.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * 流程定义数据
 */
@Entity
@Table(name = "xz_activiti_define_process_")
public class ProcessDefineEntity {

    /**
     * 流程ID
     */
    @Id
    @Column(name = "id_")
    private Long id;

    /**
     * 流程名称
     */
    @Column(name = "name_", length = 200)
    private String name;

    /**
     * 流程所包含的步骤
     */
    @OneToMany(mappedBy = "process")
    private List<StepDefineEntity> steps;
}
