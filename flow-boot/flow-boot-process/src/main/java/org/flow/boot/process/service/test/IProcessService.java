package org.flow.boot.process.service.test;

import java.util.List;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.DeploymentView;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.common.vo.process.ProcessDefinitionVO;
import org.flow.boot.process.entity.FlowInstance;
import org.flow.boot.process.form.FileForm;
import org.flow.boot.process.form.ProcessForm;

public interface IProcessService {

	public void deploy(FileForm form);

	public ProcessDefinitionVO queryDefinitionById(String processDefinitionId);

	public List<ProcessDefinitionVO> queryDefinitionList();

	public DeploymentView queryDeploymentById(String deploymentId);

	public List<DeploymentView> queryDeploymentList();

	public void deploy(ProcessForm form);

	public FlowInstanceVO start(String processId, String entityId, EntityType entityType);

	public String getRenderedHtml(String entityId, EntityType entityType);

	public FlowInstance completeTask(String entityId, EntityType entityType);

	public FlowInstance cancelTask(String entityId, EntityType entityType);

	public void clear();

}
