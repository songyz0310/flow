package org.flow.boot.process.repository;

import org.flow.boot.process.entity.FlowInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowInstanceRepository extends JpaRepository<FlowInstance, String> {

}