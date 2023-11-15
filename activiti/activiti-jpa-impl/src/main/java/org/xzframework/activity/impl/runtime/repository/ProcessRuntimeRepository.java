package org.xzframework.activity.impl.runtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xzframework.activity.impl.runtime.entity.ProcessRuntimeEntity;

public interface ProcessRuntimeRepository extends JpaRepository<ProcessRuntimeEntity, Long> {


}
