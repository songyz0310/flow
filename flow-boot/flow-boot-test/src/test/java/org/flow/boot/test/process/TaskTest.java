/**
 * 
 */
package org.flow.boot.test.process;

import java.util.List;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
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
 * @createTime 2019-06-11 16:37:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTest {

    private static final Logger logger = LoggerFactory.getLogger(InstanceTask.class);

    @Autowired
    private TaskService taskService;

    private String processInstanceId = "28c29a94-8c38-11e9-ba52-68ecc557e441";
    private String taskId = "4b2405ec-8c38-11e9-9cb8-68ecc557e441";

    @Test
    public void queryTask() {
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        logger.warn("任务个数：{}", list.size());
        for (Task task : list) {
            logger.warn("-----------------------------------------------");
            logger.warn("步骤任务信息：{}", task.getId());
            logger.warn("步骤任务信息：{}", GsonUtil.toJson(task));
        }

    }

    @Test
    public void completeTask() {
        taskService.complete(taskId);
        logger.warn("完成任务");
        
        queryTask();
    }

}
