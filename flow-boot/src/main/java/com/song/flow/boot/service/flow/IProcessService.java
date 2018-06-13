package com.song.flow.boot.service.flow;

import java.util.List;

import com.song.flow.boot.model.form.flow.FileForm;
import com.song.flow.boot.model.view.flow.DeploymentView;
import com.song.flow.boot.model.view.flow.ProcessDefinitionView;

public interface IProcessService {

	public void deploy(FileForm form);

	public ProcessDefinitionView queryDefinitionById(String processDefinitionId);

	public List<ProcessDefinitionView> queryDefinitionList();

	public DeploymentView queryDeploymentById(String deploymentId);

	public List<DeploymentView> queryDeploymentList();

	public void clear();

}
