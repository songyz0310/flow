/**
 * 
 */
package org.flow.boot.test.process;

import static org.flow.boot.test.common.MyStencilConstants.NAMESPACE;
import static org.flow.boot.test.common.MyStencilConstants.STEP_ICON;
import static org.flow.boot.test.common.MyStencilConstants.STEP_ICON_PATH;
import static org.flow.boot.test.common.MyStencilConstants.STEP_TIP;

import java.util.List;
import java.util.Map;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
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

    @Autowired
    private RepositoryService repositoryService;

    private String processDefinitionId = "test-process01:3:dd8d9e4c-3fef-11e9-92be-68ecc557e441";

    private Logger logger = LoggerFactory.getLogger(ProcessTest.class);

    @Test
    public void deploy() {
        logger.trace("trace:输出");
        logger.debug("debug:输出");
        logger.info("info:输出");
        logger.warn("warn:输出");
        logger.error("error:输出");
        // try {
        // Deployment deployment = repositoryService.createDeployment()//
        // .addClasspathResource("process201906/并行流程.bpmn20.xml")//
        // .name("测试").enableDuplicateFiltering()//
        // .deploy();
        //
        // ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
        // .deploymentId(deployment.getId())//
        // .singleResult();
        //
        // System.out.println(pd.getId());
        // System.out.println(GsonUtil.toJson((ProcessDefinitionEntityImpl) pd));
        // }
        // catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    @Test
    public void queryProcess() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(GsonUtil.toJson((ProcessDefinitionEntityImpl) processDefinition));
        }
    }

    @Test
    public void getUserTask() {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getMainProcess();
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTasks) {

            Map<String, List<ExtensionAttribute>> attributes = userTask.getAttributes();
            System.out.println(userTask.getFormKey());
            System.out.println(userTask.getCandidateUsers());
            System.out.println(userTask.getCandidateGroups());
            System.out.println(attributes);
            System.out.println(userTask.getAttributeValue(NAMESPACE, STEP_TIP));
            System.out.println(userTask.getAttributeValue(NAMESPACE, STEP_ICON));
            System.out.println(userTask.getAttributeValue(NAMESPACE, STEP_ICON_PATH));
        }

        // org.flowable.engine.impl.persistence.entity.DeploymentEntityImpl.selectDeploymentsByQueryCriteria
    }

}
