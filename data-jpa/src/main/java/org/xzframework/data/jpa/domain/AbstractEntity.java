package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {

    /**
     * 创建时间 <br>
     * 字段不能被更新
     */
    @Column(name = "created_time_", updatable = false, nullable = false)
    private final ZonedDateTime createdTime = ZonedDateTime.now();

    @Version
    @Column(name = "version_", nullable = false)
    private long version = 0L;

    @LastModifiedDate
    @Column(name = "last_modified_time_", nullable = false)
    private ZonedDateTime lastModifiedTime = ZonedDateTime.now();

    @Override
    @Nullable
    public abstract ID getId();

    @NonNull
    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    @NonNull
    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity<?> that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(getId());
    }

    /**
     * <p>
     * 对实体进行版本检查
     * </p>
     * <p>
     * <strong>默认不用调用此方法</strong>，JPA 框架会在事务提交时自动进行乐观锁检查。
     * 仅在特定场景下才需要手工调用，例如：
     * <ul>
     *   <li>需要执行大量耗时操作前，提前进行版本检查以避免无效计算</li>
     *   <li>在非事务环境下需要手动控制版本验证时机</li>
     * </ul>
     * </p>
     *
     * @param version 待检查的传入的版本号
     */
    public void checkVersion(long version) {
        if (!Objects.equals(this.version, version)) {
            throw new OptimisticLockException("该资源在其它地方被修改");
        }
    }

}