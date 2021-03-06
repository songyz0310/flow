package org.flow.boot.process.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.enums.process.StepType;
import org.flow.boot.common.util.GsonUtil;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.process.cmd.JumpToTaskCmd;
import org.flow.boot.process.entity.FlowInstance;
import org.flow.boot.process.entity.FlowInstance.Status;
import org.flow.boot.process.entity.FlowProcess;
import org.flow.boot.process.entity.FlowStep;
import org.flow.boot.process.entity.FlowStepExtense;
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
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
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
    private HistoryService historyService;
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
    public void deploy(InputStream inputStream, EntityType entityType) {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            Date now = new Date();

            Deployment deployment = repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();

            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId())
                    .singleResult();

            FlowProcess flowProcess = new FlowProcess();
            flowProcess.setProcessId(pd.getId());
            flowProcess.setProcessKey(pd.getKey());
            flowProcess.setVersion(pd.getVersion());
            flowProcess.setProcessName(pd.getName());
            flowProcess.setEntityType(entityType);
            flowProcess.setFilePath("");
            flowProcess.setCreateTime(now);
            flowProcess.setUpdateTime(now);
            flowProcessRepository.save(flowProcess);

            int stepRank = 0;
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
                    if (StringUtils.isNotEmpty(formKey)) {
                        flowStep.setPageId(formKey.replace(".html", ""));
                        flowStep.setStepType(StepType.PAGE);
                    }
                    else {
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
                    flowStepExtenseRepository.save(flowStepExtense);

                    stepRank++;
                }
            }
        }
        catch (Exception e) {
            logger.error("流程部署Zip异常，exception:{}", e);
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public FlowInstanceVO start(String processId, String entityId, EntityType entityType,
            Map<String, Object> variables) {
        Date now = new Date();
        System.out.println(GsonUtil.toJson(variables));
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId, variables);
        String instanceId = processInstance.getId();
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        FlowStep flowStep = flowStepRepository.findByProcessIdAndStepKey(processId, task.getTaskDefinitionKey());

        FlowInstance flowInstance = new FlowInstance();
        flowInstance.setInstanceId(instanceId);
        flowInstance.setEntityType(entityType);
        flowInstance.setEntityId(entityId);
        flowInstance.setProcessId(processId);
        flowInstance.setStatus(Status.STARTED);
        flowInstance.setCreateTime(now);
        flowInstance.setUpdateTime(now);
        flowInstance.setStepId(flowStep.getStepId());
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
            FlowStep flowStep = flowStepRepository.findByProcessIdAndStepKey(flowInstance.getProcessId(), stepKey);
            flowInstance.setStepId(flowStep.getStepId());
            flowInstance.setStatus(Status.RUNNING);
        }
        else {
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

        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(flowInstance.getInstanceId())//
                .orderBy(HistoricTaskInstanceQueryProperty.START)//
                .asc().list();
        int size = list.size();
        if (size >= 2) {
            Task task = managementService.executeCommand(new JumpToTaskCmd(taskService, list.get(size - 1).getId(),
                    list.get(size - 2).getTaskDefinitionKey()));
            String stepKey = task.getTaskDefinitionKey();
            FlowStep flowStep = flowStepRepository.findByProcessIdAndStepKey(flowInstance.getProcessId(), stepKey);
            flowInstance.setStepId(flowStep.getStepId());
        }

        flowInstance.setStatus(Status.RUNNING);
        flowInstance.setUpdateTime(new Date());
        flowInstanceRepository.save(flowInstance);
        return flowInstance;
    }

    @Transactional
    public FlowInstance jumpToTask(String entityId, EntityType entityType, String stepId) {
        List<FlowInstance> flowInstances = flowInstanceRepository.findByEntityTypeAndEntityId(entityType, entityId);
        FlowInstance flowInstance = flowInstances.get(0);

        Task task = taskService.createTaskQuery().processInstanceId(flowInstance.getInstanceId()).singleResult();

        FlowStep flowStep = flowStepRepository.getOne(stepId);
        task = managementService.executeCommand(new JumpToTaskCmd(taskService, task.getId(), flowStep.getStepKey()));

        String stepKey = task.getTaskDefinitionKey();
        flowStep = flowStepRepository.findByProcessIdAndStepKey(flowInstance.getProcessId(), stepKey);
        flowInstance.setStepId(flowStep.getStepId());
        flowInstance.setStatus(Status.RUNNING);
        flowInstance.setUpdateTime(new Date());
        flowInstanceRepository.save(flowInstance);
        return flowInstance;
    }

}
