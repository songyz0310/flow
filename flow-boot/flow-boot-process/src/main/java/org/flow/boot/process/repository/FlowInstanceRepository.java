package org.flow.boot.process.repository;

import java.util.List;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.process.entity.FlowInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowInstanceRepository extends JpaRepository<FlowInstance, String> {

	List<FlowInstance> findByEntityTypeAndEntityId(EntityType entityType, String entityId);

}