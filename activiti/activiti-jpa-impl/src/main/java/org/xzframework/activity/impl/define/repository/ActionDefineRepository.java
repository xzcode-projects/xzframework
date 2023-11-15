package org.xzframework.activity.impl.define.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xzframework.activity.impl.define.entity.ActionDefineEntity;

public interface ActionDefineRepository extends JpaRepository<ActionDefineEntity, Long> {
}
