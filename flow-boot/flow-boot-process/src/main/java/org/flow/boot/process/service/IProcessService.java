package org.flow.boot.process.service;

import java.util.List;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.DeploymentView;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.common.vo.process.ProcessDefinitionView;
import org.flow.boot.process.entity.FlowInstance;
import org.flow.boot.process.form.FileForm;
import org.flow.boot.process.form.ProcessForm;

public interface IProcessService {

	public void deploy(FileForm form);

	public ProcessDefinitionView queryDefinitionById(String processDefinitionId);

	public List<ProcessDefinitionView> queryDefinitionList();

	public DeploymentView queryDeploymentById(String deploymentId);

	public List<DeploymentView> queryDeploymentList();

	public void clear();

	public void deploy(ProcessForm form);

	public FlowInstanceVO start(String processId, String entityId, EntityType entityType);

	public String getRenderedHtml(String entityId, EntityType entityType);

	public FlowInstance completeTask(String entityId, EntityType entityType);

}
