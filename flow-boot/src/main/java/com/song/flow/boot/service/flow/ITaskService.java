package com.song.flow.boot.service.flow;

import java.util.List;

import com.song.flow.boot.model.view.flow.FormInfoView;
import com.song.flow.boot.model.view.flow.TaskView;

public interface ITaskService {

	public TaskView queryById(String taskId);

	public List<TaskView> queryList();

	public FormInfoView queryFormInfo(String taskId);

	public Object queryForm(String taskId);
	
}
