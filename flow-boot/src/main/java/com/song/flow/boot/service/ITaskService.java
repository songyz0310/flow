package com.song.flow.boot.service;

import java.util.List;

import com.song.flow.boot.common.view.FormInfoView;
import com.song.flow.boot.common.view.TaskView;

public interface ITaskService {

	public TaskView queryById(String taskId);

	public List<TaskView> queryList();

	public FormInfoView queryFormInfo(String taskId);

	public Object queryForm(String taskId);
	
}
