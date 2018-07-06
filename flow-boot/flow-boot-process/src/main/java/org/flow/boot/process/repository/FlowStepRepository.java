package org.flow.boot.process.repository;

import java.util.List;

import org.flow.boot.process.entity.FlowStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowStepRepository extends JpaRepository<FlowStep, String> {

	@Deprecated
	List<FlowStep> findByProcessIdOrderByStepRank(String processId);

	@Deprecated
	List<FlowStep> findByProcessIdAndStepRankLessThanEqualOrderByStepRank(String processId, int stepRank);

	FlowStep findByProcessIdAndStepKey(String processId, String stepKey);

}