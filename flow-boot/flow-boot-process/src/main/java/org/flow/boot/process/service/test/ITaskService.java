package org.flow.boot.process.service.test;

import java.util.List;

import org.flow.boot.common.vo.process.FormInfoVO;
import org.flow.boot.common.vo.process.TaskVO;

public interface ITaskService {

	public TaskVO queryById(String taskId);

	public List<TaskVO> queryList();

	public FormInfoVO queryFormInfo(String taskId);

	public Object queryForm(String taskId);

	public void completeTask(String taskId);

}
