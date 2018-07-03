package org.flow.boot.process.service;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.process.entity.FlowInstance;
import org.flow.boot.process.form.ProcessForm;

public interface FlowService {

	public void deploy(ProcessForm form);

	public FlowInstanceVO start(String processId, String entityId, EntityType entityType);

	public String getRenderedHtml(String entityId, EntityType entityType);

	public FlowInstance completeTask(String entityId, EntityType entityType);

	public FlowInstance cancelTask(String entityId, EntityType entityType);

	public FlowInstance jumpTask(String entityId, EntityType entityType, String stepId);

}
