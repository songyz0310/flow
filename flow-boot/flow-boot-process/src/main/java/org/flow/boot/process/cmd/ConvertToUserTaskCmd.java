package org.flow.boot.process.cmd;

import java.util.LinkedList;
import java.util.List;

import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.common.impl.interceptor.Command;
import org.flowable.engine.common.impl.interceptor.CommandContext;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;

public class ConvertToUserTaskCmd implements Command<Object> {

	private String processDefinitionId;
	private List<UserTask> userTasks = new LinkedList<>();

	public ConvertToUserTaskCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Object execute(CommandContext commandContext) {
		Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
		process.getFlowElements().forEach(flowElement -> {
			if (flowElement instanceof UserTask)
				userTasks.add((UserTask) flowElement);
		});
		return null;
	}

	public List<UserTask> getUserTasks() {
		return userTasks;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

}
