package org.flow.boot.process.service;

import java.util.List;

import org.flow.boot.process.form.FileForm;
import org.flow.boot.process.form.ProcessForm;
import org.flow.boot.process.view.DeploymentView;
import org.flow.boot.process.view.ProcessDefinitionView;

public interface IProcessService {

	public void deploy(FileForm form);

	public ProcessDefinitionView queryDefinitionById(String processDefinitionId);

	public List<ProcessDefinitionView> queryDefinitionList();

	public DeploymentView queryDeploymentById(String deploymentId);

	public List<DeploymentView> queryDeploymentList();

	public void clear();

	public void deploy(ProcessForm form);

}
