package org.flow.boot.ticket.repository;

import org.flow.boot.ticket.entity.SysFlowStepData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysFlowStepDataRepository extends JpaRepository<SysFlowStepData, String> {

	int deleteByStepId(String stepId);
}