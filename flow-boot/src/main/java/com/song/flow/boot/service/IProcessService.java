package com.song.flow.boot.service;

import java.util.List;

import com.song.flow.boot.common.form.FileForm;
import com.song.flow.boot.common.view.DeploymentView;
import com.song.flow.boot.common.view.ProcessDefinitionView;

public interface IProcessService {

	public void deploy(FileForm form);

	public ProcessDefinitionView queryDefinitionById(String processDefinitionId);

	public List<ProcessDefinitionView> queryDefinitionList();

	public DeploymentView queryDeploymentById(String deploymentId);

	public List<DeploymentView> queryDeploymentList();

	public void clear();

}
