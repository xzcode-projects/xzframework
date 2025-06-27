package org.xzframework.data.jpa.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@TableGenerators(
        @TableGenerator(
                name = "idGenerator",
                pkColumnName = "table_name_",
                table = "auto_pk_support_",
                valueColumnName = "next_id_"
        )
)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {
    /**
     * 创建时间 <br>
     * 字段不能被更新
     */
    @Column(name = "created_time_", updatable = false, nullable = false)
    private final ZonedDateTime createdTime = ZonedDateTime.now();

    @Version
    @Column(name = "version_", nullable = false)
    private final Long version = 0L;

    @LastModifiedDate
    @Column(name = "last_modified_time_", nullable = false)
    private ZonedDateTime lastModifiedTime = ZonedDateTime.now();


    @Override
    abstract public ID getId();

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
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
     * 1.在某些情况下，用户A打开页面对数据data进行操作，并将数据停留在页面上。<br>
     * 2.用户B打开页面,对data进行修改<br>
     * 3.用户A提交数据<br>
     * 4.用户B提交数据<br>
     * 当用户A提交数据后，如果用户B尝试提交数据，用户B实际上修改的数据是用户A修改之前的版本，而不是数据最新的版本，因为它没有感知到用户A对数据的提交<br>
     * hibernate的默认乐观锁实现仅仅在事务并发时才会生效。而我们需要的业务可能线程并没有并发，仅仅是人的并行编辑的动作，因此需要手动的对version进行检查<br>
     * 这个检查不是必须的，由各个实体根据自身的业务需求而定
     *
     * @param version 待检查的传入的版本号
     */
    public void checkVersion(Long version) {
        if (!Objects.equals(this.version, version)) {
            throw new OptimisticLockException("该资源在其它地方被修改");
        }
    }
}
