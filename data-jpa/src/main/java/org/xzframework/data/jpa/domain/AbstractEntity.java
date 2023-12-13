package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Long> {
    /**
     * 创建时间 <br>
     * 字段不能被更新
     */
    @Column(name = "created_time_", updatable = false, nullable = false)
    private final ZonedDateTime createdTime = ZonedDateTime.now();


    @Version
    @Column(name = "version_", nullable = false)
    private final Long version = 0L;

    @Column(name = "last_modified_time_", nullable = false)
    private ZonedDateTime lastModifiedTime = ZonedDateTime.now();


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

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    @PreUpdate
    public final void updateLastModifiedTime() {
        lastModifiedTime = ZonedDateTime.now();
    }
}
