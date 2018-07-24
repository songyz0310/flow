package org.flow.boot.process.cmd;

import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityManager;

/**
 * 跳跃至指定步骤
 * 
 * @author songyz
 *
 */
public class JumpToTaskCmd implements Command<Task> {

    protected String taskId;

    protected String flowElementId;

    protected TaskService taskService;

    public JumpToTaskCmd(TaskService taskService, String taskId, String flowElementId) {
        this.taskId = taskId;
        this.taskService = taskService;
        this.flowElementId = flowElementId;
    }

    public Task execute(CommandContext commandContext) {

        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager();
        TaskEntityManager taskEntityManager = org.flowable.task.service.impl.util.CommandContextUtil
                .getTaskEntityManager();
        TaskEntity taskEntity = taskEntityManager.findById(taskId);
        ExecutionEntity executionEntity = executionEntityManager.findById(taskEntity.getExecutionId());
        Process process = ProcessDefinitionUtil.getProcess(executionEntity.getProcessDefinitionId());

        executionEntity.setCurrentFlowElement(process.getFlowElement(flowElementId));

        CommandContextUtil.getAgenda().planContinueProcessInCompensation(executionEntity);

        CommandContextUtil.getIdentityLinkService().deleteIdentityLinksByTaskId(taskId);

        taskEntityManager.delete(taskId);

        return taskService.createTaskQuery()//
                .processDefinitionId(executionEntity.getProcessDefinitionId())//
                .singleResult();

    }

}
