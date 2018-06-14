package org.flow.boot.process.repository;

import org.flow.boot.process.entity.FlowPageItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowPageItemRepository extends JpaRepository<FlowPageItem, String> {

}