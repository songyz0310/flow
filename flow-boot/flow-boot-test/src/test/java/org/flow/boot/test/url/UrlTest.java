package org.flow.boot.test.url;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    private Map<String, Object> variables = new HashMap<>();

//    private String processDefinitionId = "Process_1:3:66f149d6-3ff4-11e9-bd3e-68ecc557e441";
    private String processDefinitionId = "Process_1:4:e34f87af-3ff8-11e9-bb5c-68ecc557e441";

    @Before
    public void beforeClass() {
        variables.put("con", "BB");
    }

    @Test
    public void testDeploy() {
        String name = "测试URL部署流程";
        String url = "https://dev.1stcs.cn/files/v2/public/2/process/c69b8560-3ff1-11e9-bd08-7dcf7653b032_1551864325233.xml";
        try (InputStream inputStream = new URL(url).openStream()) {
            Deployment deployment = repositoryService.createDeployment()//
                    .addInputStream(name + "bpmn", inputStream)//
                    .enableDuplicateFiltering()//
                    .name(name)//
                    .deploy();

            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
                    .deploymentId(deployment.getId())//
                    .singleResult();

            System.out.println(pd.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStartInstance() {
        
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        String processInstanceId = processInstance.getId();
        System.out.println(processInstanceId);
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        list.forEach(task -> {
            System.out.println("-------------");
            System.out.println(task.getId());
            System.out.println(task.getName());
        });
    }

}
