package org.flow.boot.process.service.test;

import java.util.List;

import org.flow.boot.common.vo.process.DeploymentView;
import org.flow.boot.common.vo.process.ProcessDefinitionVO;
import org.flow.boot.process.form.FileForm;

public interface TestFlowService {

	public void deploy(FileForm form);

	public ProcessDefinitionVO queryDefinitionById(String processDefinitionId);

	public List<ProcessDefinitionVO> queryDefinitionList();

	public DeploymentView queryDeploymentById(String deploymentId);

	public List<DeploymentView> queryDeploymentList();

	public void clear();

}
