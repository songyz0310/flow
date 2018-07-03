package org.flow.boot.process.cmd;

import org.flowable.bpmn.model.Process;
import org.flowable.engine.common.impl.interceptor.Command;
import org.flowable.engine.common.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityManager;

public class JumpTaskCmd implements Command<Object> {

	protected String taskId;

	protected String target;

	public JumpTaskCmd(String taskId, String target) {
		this.taskId = taskId;
		this.target = target;
	}

	public Void execute(CommandContext commandContext) {

		ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager();
		TaskEntityManager taskEntityManager = org.flowable.task.service.impl.util.CommandContextUtil
				.getTaskEntityManager();
		TaskEntity taskEntity = taskEntityManager.findById(taskId);
		ExecutionEntity ee = executionEntityManager.findById(taskEntity.getExecutionId());
		Process process = ProcessDefinitionUtil.getProcess(ee.getProcessDefinitionId());

		ee.setCurrentFlowElement(process.getFlowElement(target));

		CommandContextUtil.getAgenda().planContinueProcessInCompensation(ee);

		taskEntityManager.delete(taskId);

		return null;

	}

}
