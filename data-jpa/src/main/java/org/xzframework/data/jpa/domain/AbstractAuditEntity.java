package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity<UID extends Serializable, UNAME extends Serializable> extends AbstractEntity {

    /**
     * 创建时间 <br></br>
     * 字段不能被更新
     */
    @Column(name = "created_time_", updatable = false, nullable = false)
    private final ZonedDateTime createdTime = ZonedDateTime.now();
    @Column(name = "last_modified_time_", nullable = false)
    private ZonedDateTime lastModifiedTime = ZonedDateTime.now();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "userid",
                    column = @Column(name = "created_user_id_", updatable = false)
            ),
            @AttributeOverride(
                    name = "username",
                    column = @Column(name = "created_user_name_", updatable = false)
            )
    })
    private Auditor<UID, UNAME> createdBy;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "userid",
                    column = @Column(name = "last_modified_user_id_")
            ),
            @AttributeOverride(
                    name = "username",
                    column = @Column(name = "last_modified_user_name_")
            )
    })
    private Auditor<UID, UNAME> lastModifiedBy;

    public AbstractAuditEntity() {
    }

    public AbstractAuditEntity(Long id) {
        super(id);
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public Optional<Auditor<UID, UNAME>> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @CreatedBy
    public void setCreatedBy(Auditor<UID, UNAME> createdBy) {
        this.createdBy = createdBy;
    }

    public Optional<Auditor<UID, UNAME>> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @LastModifiedBy
    public void setLastModifiedBy(Auditor<UID, UNAME> lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @PreUpdate
    public final void updateLastModifiedTime() {
        lastModifiedTime = ZonedDateTime.now();
    }
}
