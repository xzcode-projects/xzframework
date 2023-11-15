package org.xzframework.activity.impl.runtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.xzframework.activity.impl.runtime.entity.ExecuteHistoryEntity;

import java.util.List;

public interface ExecuteHistoryRepository extends JpaRepository<ExecuteHistoryEntity, Long> {


    @Query("select eh from ExecuteHistoryEntity eh where eh.action.step.process.id=:processId order by eh.executeTime desc")
    List<ExecuteHistoryEntity> findByProcessId(@Param("processId") Long processId);
}
