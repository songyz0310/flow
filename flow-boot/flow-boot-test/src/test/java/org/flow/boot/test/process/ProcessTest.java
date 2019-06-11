/**
 * 
 */
package org.flow.boot.test.process;

import static org.flow.boot.common.MyStencilConstants.NAMESPACE;
import static org.flow.boot.common.MyStencilConstants.STEP_ICON;
import static org.flow.boot.common.MyStencilConstants.STEP_ICON_PATH;
import static org.flow.boot.common.MyStencilConstants.STEP_TIP;

import java.util.List;
import java.util.Map;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author songyz<br>
 * @createTime 2019-06-10 14:42:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessTest {

    private static final Logger logger = LoggerFactory.getLogger(ProcessTest.class);

    @Autowired
    private RepositoryService repositoryService;

    private String processDefinitionId = "simpleProcess:2:70695706-8c1e-11e9-b95a-68ecc557e441";

    @Test
    public void deploy() {

        try {
            Deployment deployment = repositoryService.createDeployment()//
                    .addClasspathResource("process201906/并行流程3.bpmn20.xml")//
                    .name("测试").enableDuplicateFiltering()//
                    .deploy();

            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
                    .deploymentId(deployment.getId())//
                    .singleResult();

            logger.warn("流程ID：{}", pd.getId());
            logger.warn("流程信息：{}", GsonUtil.toJson(pd));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryProcess() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition processDefinition : list) {
            logger.info("流程信息：{}", GsonUtil.toJson(processDefinition));
        }
    }

    @Test
    public void getUserTask() {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getMainProcess();
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTasks) {

            Map<String, List<ExtensionAttribute>> attributes = userTask.getAttributes();
            logger.info("------------------------------------------------------------");
            logger.info("步骤任务信息：{}", userTask.getName());
            logger.info("步骤任务信息：{}", userTask.getFormKey());
            logger.info("步骤任务信息：{}", userTask.getCandidateUsers());
            logger.info("步骤任务信息：{}", userTask.getCandidateGroups());
            logger.info("步骤任务信息：{}", attributes);
            logger.info("步骤任务信息：{}", userTask.getAttributeValue(NAMESPACE, STEP_TIP));
            logger.info("步骤任务信息：{}", userTask.getAttributeValue(NAMESPACE, STEP_ICON));
            logger.info("步骤任务信息：{}", userTask.getAttributeValue(NAMESPACE, STEP_ICON_PATH));
        }

    }

}
