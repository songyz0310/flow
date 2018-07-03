package org.flow.boot.process.repository;

import java.util.List;

import org.flow.boot.process.entity.FlowStepExtense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowStepExtenseRepository extends JpaRepository<FlowStepExtense, String> {

	List<FlowStepExtense> findByStepStatus(String stepStatus);
	
}