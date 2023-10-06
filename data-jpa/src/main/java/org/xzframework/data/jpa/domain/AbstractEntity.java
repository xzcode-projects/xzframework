package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @TableGenerator(
            name = "tableGenerator",
            pkColumnName = "table_name_",
            table = "auto_pk_support_",
            valueColumnName = "next_id_"
    )
    @Column(name = "id_", updatable = false)
    private Long id;


    @Version
    @Column(name = "version_", nullable = false)
    private final Long version = 0L;

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
