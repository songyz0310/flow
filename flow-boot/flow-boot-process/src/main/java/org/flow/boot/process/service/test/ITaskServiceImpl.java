package org.flow.boot.process.service.test;

import java.util.LinkedList;
import java.util.List;

import org.flow.boot.common.vo.process.FormInfoVO;
import org.flow.boot.common.vo.process.FormModelVO;
import org.flow.boot.common.vo.process.TaskVO;
import org.flowable.engine.FormService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.TaskFormData;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormModel;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITaskServiceImpl implements ITaskService {

	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;

	public TaskVO queryById(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskVO vo = new TaskVO();
		BeanUtils.copyProperties(task, vo);
		return vo;
	}

	public List<TaskVO> queryList() {
		List<Task> list = taskService.createTaskQuery().list();
		List<TaskVO> data = new LinkedList<>();
		list.forEach(fd -> {
			TaskVO vo = new TaskVO();
			BeanUtils.copyProperties(fd, vo);
			data.add(vo);
		});
		list.clear();
		return data;
	}

	public FormInfoVO queryFormInfo(String taskId) {
		FormInfo formInfo = taskService.getTaskFormModel(taskId);
		FormModel formModel = formInfo.getFormModel();
		FormInfoVO vo = new FormInfoVO();
		FormModelVO formModelView = new FormModelVO();
		BeanUtils.copyProperties(formInfo, vo);
		BeanUtils.copyProperties(formModel, formModelView);
		vo.setFormModel(formModelView);
		return vo;
	}

	public Object queryForm(String taskId) {
		TaskFormData taskFormData = formService.getTaskFormData(taskId);// 内置表单（直接在流程中定义）
		System.out.println(taskFormData);
		Object renderedTaskForm = formService.getRenderedTaskForm(taskId);// 这个方法是返回一个纯文本的（外置表单（一个.form结尾的文件，），可以是一个div标签）
		System.out.println(renderedTaskForm);
		return renderedTaskForm;
	}

	public void completeTask(String taskId) {
		taskService.complete(taskId);
		// String processDefinitionId = "";
		// taskService.createTaskQuery().processInstanceId(processInstanceId)

	}

}
