package org.flow.boot.process.repository;

import java.util.List;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.process.entity.FlowProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowProcessRepository extends JpaRepository<FlowProcess, String> {

	List<FlowProcess> findByEntityType(EntityType entityType);

}