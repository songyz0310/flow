/**
 * 
 */
package org.flow.boot.test.process;

import java.util.List;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
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
 * @createTime 2019-06-11 16:35:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InstanceTask {

    private static final Logger logger = LoggerFactory.getLogger(InstanceTask.class);

    @Autowired
    private RuntimeService runtimeService;

    private String processDefinitionId = "simpleProcess:2:19c26c52-8c38-11e9-ac34-68ecc557e441";

    @Test
    public void startInstance() {
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
            logger.info("实例信息：{}", processInstance.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryInstance() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        logger.warn("实例个数：{}", list.size());
        for (ProcessInstance processInstance : list) {
            logger.warn("实例信息：{}", GsonUtil.toJson(processInstance));
        }
    }

}
