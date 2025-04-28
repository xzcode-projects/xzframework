package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity<UID extends Serializable> extends AbstractEntity {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "userid",
                    column = @Column(name = "created_user_id_", updatable = false)
            ),
            @AttributeOverride(
                    name = "username",
                    column = @Column(name = "created_user_name_", updatable = false, length = 100)
            )
    })
    private Auditor<UID> createdBy;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "userid",
                    column = @Column(name = "last_modified_user_id_")
            ),
            @AttributeOverride(
                    name = "username",
                    column = @Column(name = "last_modified_user_name_", length = 100)
            )
    })
    private Auditor<UID> lastModifiedBy;

    public AbstractAuditEntity() {
    }

    public AbstractAuditEntity(Long id) {
        super(id);
    }

    public Optional<Auditor<UID>> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @CreatedBy
    public void setCreatedBy(Auditor<UID> createdBy) {
        this.createdBy = createdBy;
    }

    public Optional<Auditor<UID>> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @LastModifiedBy
    public void setLastModifiedBy(Auditor<UID> lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
