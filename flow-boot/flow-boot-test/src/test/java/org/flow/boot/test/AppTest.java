package org.flow.boot.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.engine.FormService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
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
    private FormService formService;
    @Autowired
    private RuntimeService runtimeService;

    private String processDefinitionId = "test-process01:3:dd8d9e4c-3fef-11e9-92be-68ecc557e441";
    private String processInstanceId = "416a7f9e-3ff0-11e9-af58-68ecc557e441";
    
    private Map<String, Object> variables = new HashMap<>();
    {
        variables.put("con", "C");
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
            System.out.println(task.getAssignee());
            System.out.println(formService.getRenderedTaskForm(task.getId()));
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

   

}
