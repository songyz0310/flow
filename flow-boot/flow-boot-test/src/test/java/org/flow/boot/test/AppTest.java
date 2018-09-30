package org.flow.boot.test;

import static org.flow.boot.test.common.MyStencilConstants.NAMESPACE;
import static org.flow.boot.test.common.MyStencilConstants.STEP_ICON;
import static org.flow.boot.test.common.MyStencilConstants.STEP_ICON_PATH;
import static org.flow.boot.test.common.MyStencilConstants.STEP_TIP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flow.boot.common.util.GsonUtil;
import org.flow.boot.test.cmd.CancelTaskCmd;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ManagementService managementService;

    private Map<String, Object> variables = new HashMap<>();
    {
        variables.put("con", "AA");
    }

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment()//
                // .addClasspathResource("process.bpmn20.xml")//
                // .addClasspathResource("test02.bpmn20.xml")//
                .addClasspathResource("1_test02.bpmn20.xml")//
                .deploy();

        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
                .deploymentId(deployment.getId())//
                .singleResult();

        System.out.println(GsonUtil.toJson((ProcessDefinitionEntityImpl) pd));
    }

    @Test
    public void queryProcess() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(GsonUtil.toJson((ProcessDefinitionEntityImpl) processDefinition));
        }
    }

    @Test
    public void queryInstance() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance processInstance : list) {
            System.out.println(GsonUtil.toJson((ExecutionEntityImpl) processInstance));
        }
    }

    @Test
    public void queryTask() {
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task task : list) {
            System.out.println("-------------");
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }

    @Test
    public void startInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        System.out.println(processInstance.getId());
    }

    @Test
    public void completeTask() {
        variables.put("con", "B");

        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task task : list) {

            taskService.complete(task.getId(), variables);
        }

    }

    @Test
    public void getUserTask() {
//        BpmnModel bpmnModel = repositoryService.getBpmnModel("test02:2:5865dd30-c492-11e8-b29a-68ecc557e441");
        BpmnModel bpmnModel = repositoryService.getBpmnModel("test02:3:2a133afa-c493-11e8-95fb-68ecc557e441");
        Process process = bpmnModel.getMainProcess();
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTasks) {
            System.out.println(userTask.getAttributeValue(NAMESPACE, STEP_TIP));
            System.out.println(userTask.getAttributeValue(NAMESPACE, STEP_ICON));
            System.out.println(userTask.getAttributeValue(NAMESPACE, STEP_ICON_PATH));
        }
    }

    @Test
    public void testCancel() {

        String instanceId = processInstanceId;
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()//
                .processInstanceId(instanceId)//
                .orderBy(HistoricTaskInstanceQueryProperty.START)// 开始时间
                .asc()// 正序
                .list();
        int size = list.size();
        if (size >= 2) {
            String currentTaskId = list.get(size - 1).getId();
            String targetTaskId = list.get(size - 2).getId();
            managementService.executeCommand(new CancelTaskCmd(currentTaskId, targetTaskId));

        }
    }

    // String processDefinitionId = "process:1:288e38a8-c233-11e8-8845-68ecc557e441";
    // String processInstanceId = "70099da7-c23a-11e8-911f-68ecc557e441";
    // String processDefinitionId = "test02:1:14430f21-c23e-11e8-951b-68ecc557e441";
    // String processInstanceId = "48a21511-c23f-11e8-a727-68ecc557e441";
    String processDefinitionId = "test02:2:5865dd30-c492-11e8-b29a-68ecc557e441";
    String processInstanceId = "d1ba5612-c48e-11e8-98fd-68ecc557e441";

}
