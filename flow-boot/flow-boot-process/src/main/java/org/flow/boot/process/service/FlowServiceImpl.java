package org.flow.boot.process.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.enums.StepType;
import org.flow.boot.common.enums.TicketStatus;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.process.cmd.JumpTaskCmd;
import org.flow.boot.process.entity.FlowInstance;
import org.flow.boot.process.entity.FlowInstance.Status;
import org.flow.boot.process.entity.FlowProcess;
import org.flow.boot.process.entity.FlowStep;
import org.flow.boot.process.entity.FlowStepExtense;
import org.flow.boot.process.form.ProcessForm;
import org.flow.boot.process.repository.FlowInstanceRepository;
import org.flow.boot.process.repository.FlowProcessRepository;
import org.flow.boot.process.repository.FlowStepExtenseRepository;
import org.flow.boot.process.repository.FlowStepRepository;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CustomProperty;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.FormService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowServiceImpl implements FlowService {

	private static final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);

	@Autowired
	private FormService formService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private FlowStepRepository flowStepRepository;
	@Autowired
	private FlowProcessRepository flowProcessRepository;
	@Autowired
	private FlowStepExtenseRepository flowStepExtenseRepository;
	@Autowired
	private FlowInstanceRepository flowInstanceRepository;

	@Transactional
	public void deploy(ProcessForm form) {
		try (ZipInputStream inputStream = new ZipInputStream(form.getFile())) {
			Date now = new Date();

			Deployment deployment = repositoryService.createDeployment()//
					.name(form.getName()).addZipInputStream(inputStream)//
					.deploy();

			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
					.deploymentId(deployment.getId()).singleResult();

			FlowProcess flowProcess = new FlowProcess();
			flowProcess.setProcessId(pd.getId());
			flowProcess.setCreateTime(now);
			flowProcess.setEntityType(EntityType.TICKET);
			flowProcess.setFilePath("");
			flowProcess.setProcessKey(form.getKey());
			flowProcess.setProcessName(form.getName());
			flowProcess.setUpdateTime(now);
			flowProcessRepository.save(flowProcess);

			int stepRank = 0;
			String fromStatus = null;
			BpmnModel bpmnModel = repositoryService.getBpmnModel(pd.getId());
			Process mainProcess = bpmnModel.getMainProcess();
			Collection<FlowElement> flowElements = mainProcess.getFlowElements();
			for (FlowElement flowElement : flowElements) {
				if (flowElement instanceof UserTask) {
					UserTask userTask = (UserTask) flowElement;
					FlowStep flowStep = new FlowStep();
					flowStep.setProcessId(pd.getId());
					flowStep.setStepName(userTask.getName());
					flowStep.setStepRank(stepRank);
					flowStep.setStepKey(userTask.getId());

					String formKey = userTask.getFormKey();
					if (Objects.equals(formKey, StepType.SIGNIN.name())) {
						flowStep.setStepType(StepType.SIGNIN);
					} else if (StringUtils.isNotEmpty(formKey)) {
						flowStep.setPageId(formKey.replace(".html", ""));
						flowStep.setStepType(StepType.PAGE);
					} else {
						flowStep.setStepType(StepType.EXECUTE);
					}

					List<CustomProperty> customProperties = userTask.getCustomProperties();
					for (CustomProperty customProperty : customProperties) {
						customProperty.getName();
						customProperty.getSimpleValue();
						// 保存步骤扩展信息
					}

					flowStepRepository.save(flowStep);

					FlowStepExtense flowStepExtense = new FlowStepExtense();
					flowStepExtense.setStepId(flowStep.getStepId());
					flowStepExtense.setCandidateGroups(userTask.getCandidateGroups());
					flowStepExtense.setFromStatus(fromStatus);
					switch (flowStep.getStepName()) {
					case "接单":
						flowStepExtense.setToStatus(TicketStatus.RECEIVED.name());
						break;
					case "预约":
						flowStepExtense.setToStatus(TicketStatus.APPOINTED.name());
						break;
					case "出发":
						flowStepExtense.setToStatus(TicketStatus.SETOUT.name());
						break;
					case "到场":
						flowStepExtense.setToStatus(TicketStatus.ARRIVED.name());
						break;
					case "完成":
						flowStepExtense.setToStatus(TicketStatus.FINISHED.name());
						break;
					case "关单":
						flowStepExtense.setToStatus(TicketStatus.CLOSE.name());
						break;
					default:
						flowStepExtense.setToStatus(TicketStatus.BILLED.name());
						break;
					}
					fromStatus = flowStepExtense.getToStatus();
					flowStepExtenseRepository.save(flowStepExtense);

					stepRank++;
				}
			}
		} catch (Exception e) {
			logger.error("流程部署Zip异常，exception:{}", e);
			throw new RuntimeException(e);
		}

	}

	@Transactional
	public FlowInstanceVO start(String processId, String entityId, EntityType entityType) {
		Date now = new Date();
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId);
		FlowInstance flowInstance = new FlowInstance();
		flowInstance.setInstanceId(processInstance.getId());
		flowInstance.setEntityType(entityType);
		flowInstance.setEntityId(entityId);
		flowInstance.setProcessId(processId);
		flowInstance.setStatus(Status.STARTED);
		flowInstance.setCreateTime(now);
		flowInstance.setUpdateTime(now);
		List<FlowStep> flowSteps = flowStepRepository.findByProcessIdOrderByStepRank(processId);
		if (flowSteps.isEmpty() == false && Objects.nonNull(flowSteps.get(0))) {
			flowInstance.setStepId(flowSteps.get(0).getStepId());
		}

		flowInstanceRepository.save(flowInstance);

		FlowInstanceVO vo = new FlowInstanceVO();
		BeanUtils.copyProperties(flowInstance, vo);
		return vo;
	}

	public String getRenderedHtml(String entityId, EntityType entityType) {
		List<FlowInstance> flowInstances = flowInstanceRepository.findByEntityTypeAndEntityId(entityType, entityId);
		String instanceId = flowInstances.get(0).getInstanceId();
		String taskId = taskService.createTaskQuery().processInstanceId(instanceId).singleResult().getId();
		return formService.getRenderedTaskForm(taskId).toString();// 这个方法是返回一个纯文本的HTML
	}

	@Transactional
	public FlowInstance completeTask(String entityId, EntityType entityType) {
		List<FlowInstance> flowInstances = flowInstanceRepository.findByEntityTypeAndEntityId(entityType, entityId);
		FlowInstance flowInstance = flowInstances.get(0);
		String instanceId = flowInstance.getInstanceId();
		String taskId = taskService.createTaskQuery().processInstanceId(instanceId).singleResult().getId();
		taskService.complete(taskId);

		Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		if (Objects.nonNull(task)) {
			String stepKey = task.getTaskDefinitionKey();
			FlowStep flowStep = flowStepRepository.findByStepKey(stepKey);
			flowInstance.setStepId(flowStep.getStepId());
			flowInstance.setStatus(Status.RUNNING);
		} else {
			flowInstance.setStepId(null);
			flowInstance.setStatus(Status.STOPED);
		}
		flowInstance.setUpdateTime(new Date());
		flowInstanceRepository.save(flowInstance);
		return flowInstance;

	}

	@Transactional
	public FlowInstance cancelTask(String entityId, EntityType entityType) {
		List<FlowInstance> flowInstances = flowInstanceRepository.findByEntityTypeAndEntityId(entityType, entityId);
		FlowInstance flowInstance = flowInstances.get(0);

		String instanceId = flowInstance.getInstanceId();

		FlowStep flowStep = flowStepRepository.findOne(flowInstance.getStepId());
		List<FlowStep> flowStepList = flowStepRepository.findByProcessIdAndStepRankLessThanEqualOrderByStepRank(
				flowStep.getProcessId(), flowStep.getStepRank());
		int size = flowStepList.size();
		if (size >= 2) {
			runtimeService.createChangeActivityStateBuilder().processInstanceId(instanceId)
					.moveActivityIdTo(flowStepList.get(size - 1).getStepKey(), flowStepList.get(size - 2).getStepKey())
					.changeState();
		}

		Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		if (Objects.nonNull(task)) {
			String stepKey = task.getTaskDefinitionKey();
			flowStep = flowStepRepository.findByStepKey(stepKey);
			flowInstance.setStepId(flowStep.getStepId());
			flowInstance.setStatus(Status.RUNNING);
		}
		flowInstance.setUpdateTime(new Date());
		flowInstanceRepository.save(flowInstance);
		return flowInstance;
	}

	@Transactional
	public FlowInstance jumpTask(String entityId, EntityType entityType, String stepId) {
		List<FlowInstance> flowInstances = flowInstanceRepository.findByEntityTypeAndEntityId(entityType, entityId);
		FlowInstance flowInstance = flowInstances.get(0);

		String instanceId = flowInstance.getInstanceId();
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();

		FlowStep flowStep = flowStepRepository.findOne(stepId);
		managementService.executeCommand(new JumpTaskCmd(task.getId(), flowStep.getStepKey()));

		task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		if (Objects.nonNull(task)) {
			String stepKey = task.getTaskDefinitionKey();
			flowStep = flowStepRepository.findByStepKey(stepKey);
			flowInstance.setStepId(flowStep.getStepId());
			flowInstance.setStatus(Status.RUNNING);
		} else {
			flowInstance.setStepId(null);
			flowInstance.setStatus(Status.STOPED);
		}
		flowInstance.setUpdateTime(new Date());
		flowInstanceRepository.save(flowInstance);
		return flowInstance;
	}

}
