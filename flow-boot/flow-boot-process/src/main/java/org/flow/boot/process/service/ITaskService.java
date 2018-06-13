package org.flow.boot.process.service;

import java.util.List;

import org.flow.boot.process.view.FormInfoView;
import org.flow.boot.process.view.TaskView;

public interface ITaskService {

	public TaskView queryById(String taskId);

	public List<TaskView> queryList();

	public FormInfoView queryFormInfo(String taskId);

	public Object queryForm(String taskId);
	
}
