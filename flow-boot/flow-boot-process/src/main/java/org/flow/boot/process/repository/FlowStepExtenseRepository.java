package org.flow.boot.process.repository;

import java.util.List;

import org.flow.boot.process.entity.FlowStepExtense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlowStepExtenseRepository extends JpaRepository<FlowStepExtense, String> {

	@Query("SELECT fse FROM FlowStep fs,FlowStepExtense fse WHERE fs.processId = ?1 and fse.toStatus = ?2 and fs.stepId = fse.stepId")
	List<FlowStepExtense> findByToStatus(String processId, String toStatus);

}