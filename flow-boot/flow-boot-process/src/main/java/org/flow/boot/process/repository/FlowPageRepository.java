package org.flow.boot.process.repository;

import org.flow.boot.process.entity.FlowPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowPageRepository extends JpaRepository<FlowPage, String> {

}