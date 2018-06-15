package org.flow.boot.process.service;

import java.util.List;

import org.flow.boot.common.vo.process.FormInfoView;
import org.flow.boot.common.vo.process.TaskView;

public interface ITaskService {

	public TaskView queryById(String taskId);

	public List<TaskView> queryList();

	public FormInfoView queryFormInfo(String taskId);

	public Object queryForm(String taskId);

	public void completeTask(String taskId);

}
