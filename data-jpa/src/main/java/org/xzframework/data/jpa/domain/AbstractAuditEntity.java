package org.xzframework.data.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity<ID extends Serializable & Comparable<ID>, UID extends Serializable & Comparable<UID>> extends AbstractEntity<ID> {

    @Column(name = "created_user_id_", updatable = false)
    private UID createdUserId;

    @Column(name = "last_modified_user_id_")
    private UID lastModifiedUserId;

    @Column(name = "created_user_name_", updatable = false, length = 100)
    private String createdUserName;

    @Column(name = "last_modified_user_name_", length = 100)
    private String lastModifiedUserName;

    @NonNull
    @CreatedBy
    public Optional<Auditor<UID>> getCreatedBy() {
        return Optional.ofNullable(createdUserId)
                .map(uid -> new Auditor<>(uid, createdUserName));
    }

    @CreatedBy
    public void setCreatedBy(@NonNull Auditor<UID> createdBy) {
        this.createdUserId = createdBy.getUserid();
        this.createdUserName = createdBy.getUsername();
    }

    @NonNull
    @LastModifiedBy
    public Optional<Auditor<UID>> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedUserId).map(
                uid -> new Auditor<>(uid, lastModifiedUserName)
        );
    }

    @LastModifiedBy
    public void setLastModifiedBy(@NonNull Auditor<UID> lastModifiedBy) {
        this.lastModifiedUserId = lastModifiedBy.getUserid();
        this.lastModifiedUserName = lastModifiedBy.getUsername();
    }

}
