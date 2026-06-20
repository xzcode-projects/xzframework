package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass
public abstract class AbstractAuditEntity<ID extends Serializable & Comparable<ID>, UID extends Serializable & Comparable<UID>> extends AbstractEntity<ID> {

    /*
     * spring data jpa audit 的created by 只支持字段。不能在setter里面做字段拆分
     */
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

    @NonNull
    public Optional<Auditor<UID>> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @CreatedBy
    public void setCreatedBy(@NonNull Auditor<UID> createdBy) {
        this.createdBy = createdBy;
    }

    @NonNull
    public Optional<Auditor<UID>> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @LastModifiedBy
    public void setLastModifiedBy(@NonNull Auditor<UID> lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
