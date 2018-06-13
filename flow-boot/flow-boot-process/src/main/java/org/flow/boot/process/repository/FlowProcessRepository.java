package org.flow.boot.process.repository;

import org.flow.boot.process.entity.FlowProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowProcessRepository extends JpaRepository<FlowProcess, String> {

}