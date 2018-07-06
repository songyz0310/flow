package org.flow.boot.process.service;

import java.io.InputStream;
import java.util.Map;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.process.entity.FlowInstance;

public interface FlowService {

	public void deploy(InputStream inputStream, EntityType entityType);

	public FlowInstanceVO start(String processId, String entityId, EntityType entityType,
			Map<String, Object> variables);

	public String getRenderedHtml(String entityId, EntityType entityType);

	public FlowInstance completeTask(String entityId, EntityType entityType);

	public FlowInstance cancelTask(String entityId, EntityType entityType);

	public FlowInstance jumpToTask(String entityId, EntityType entityType, String stepId);

}
