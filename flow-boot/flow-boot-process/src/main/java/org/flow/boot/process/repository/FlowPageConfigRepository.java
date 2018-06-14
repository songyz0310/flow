package org.flow.boot.process.repository;

import java.util.List;

import org.flow.boot.process.entity.FlowPageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowPageConfigRepository extends JpaRepository<FlowPageConfig, String> {

	List<FlowPageConfig> findByPageId(String pageId);
	
}