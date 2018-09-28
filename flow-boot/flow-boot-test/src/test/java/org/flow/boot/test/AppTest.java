package org.flow.boot.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
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

    private Map<String, Object> variables = new HashMap<>();
    {
        variables.put("con", "AA");
    }

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment()//
                // .addClasspathResource("process.bpmn20.xml")//
                // .addClasspathResource("test02.bpmn20.xml")//
                .addClasspathResource("测试条件流程.bpmn20.xml")//
                .deploy();

        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
                .deploymentId(deployment.getId())//
                .singleResult();

        System.out.println(GsonUtil.toJson((ProcessDefinitionEntityImpl) pd));
        System.out.println(historyService);
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
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
            System.out.println(processInstance.getId());
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void completeTask() {
        variables.put("con", "B");

        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task task : list) {

            taskService.complete(task.getId(), variables);
        }

    }

    // String processDefinitionId = "process:1:288e38a8-c233-11e8-8845-68ecc557e441";
    // String processInstanceId = "70099da7-c23a-11e8-911f-68ecc557e441";
    // String processDefinitionId = "test02:1:14430f21-c23e-11e8-951b-68ecc557e441";
    // String processInstanceId = "48a21511-c23f-11e8-a727-68ecc557e441";
    String processDefinitionId = "test-process01:1:2e641d8b-c2f1-11e8-b9a8-68ecc557e441";
    String processInstanceId = "48a21511-c23f-11e8-a727-68ecc557e441";

}
