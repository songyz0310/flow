package org.flow.boot.process.repository;

import org.flow.boot.process.entity.FlowStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowStepRepository extends JpaRepository<FlowStep, String> {

}