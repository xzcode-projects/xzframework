package org.xzframework.activity.impl.embedded;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Long> {
    @Version
    @Column(name = "version_", nullable = false)
    private final Long version = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @TableGenerator(
            name = "activitiTableGenerator",
            pkColumnName = "table_name_",
            table = "activiti_pk_support_",
            valueColumnName = "next_id_"
    )
    @Column(name = "id_", updatable = false)
    private Long id;

    public AbstractEntity() {
    }

    public AbstractEntity(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
