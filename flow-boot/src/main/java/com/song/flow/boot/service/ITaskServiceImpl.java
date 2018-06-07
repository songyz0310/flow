package com.song.flow.boot.service;

import java.util.LinkedList;
import java.util.List;

import org.flowable.engine.TaskService;
import org.flowable.form.api.FormInfo;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.song.flow.boot.common.view.TaskView;

@Service
public class ITaskServiceImpl implements ITaskService {

	@Autowired
	private TaskService taskService;
	
	public TaskView queryById(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskView vo = new TaskView();
		BeanUtils.copyProperties(task, vo);
		return vo;
	}

	public List<TaskView> queryList() {
		List<Task> list = taskService.createTaskQuery().list();
		List<TaskView> data = new LinkedList<>();
		list.forEach(fd -> {
			TaskView vo = new TaskView();
			BeanUtils.copyProperties(fd, vo);
			data.add(vo);
		});
		list.clear();
		return data;
	}
	
	public void queryFormModel(String taskId) {
		FormInfo formModel = taskService.getTaskFormModel(taskId);
		System.out.println(formModel);
	}
	

}
