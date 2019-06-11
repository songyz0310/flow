package org.flow.boot.test.migration;

import java.util.List;

import org.flow.boot.common.util.GsonUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.migration.ProcessInstanceMigrationValidationResult;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MigrationTest {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    private String processDefinitionId1 = "process:3:4258fcbb-d6b6-11e8-8489-68ecc557e441";
    private String processDefinitionId2 = "process:4:5c4779f7-d6b6-11e8-bf5e-68ecc557e441";

    private String processInstanceId1 = "4f82b26b-d6b5-11e8-aeb9-68ecc557e441";

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment()//
                .addClasspathResource("process1023/测试流程002.bpmn20.xml")//
                .deploy();

        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
                .deploymentId(deployment.getId())//
                .singleResult();

        System.out.println(pd.getId());
        System.out.println(GsonUtil.toJson((ProcessDefinitionEntityImpl) pd));
    }

    @Test
    public void startInstance() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId1);
        System.out.println(processInstance.getId());
    }

    @Test
    public void testMigration() {
        // 校验流程实例是否可以进行迁移
        ProcessInstanceMigrationValidationResult validationResult = runtimeService
                .createProcessInstanceMigrationBuilder()//
                .migrateToProcessDefinition(processDefinitionId2)//
                .validateMigration(processInstanceId1);

        System.out.println(validationResult.isMigrationValid());
        System.out.println(validationResult.getValidationMessages());
        
        if (validationResult.isMigrationValid()) {
            runtimeService.createProcessInstanceMigrationBuilder()//
                    .migrateToProcessDefinition(processDefinitionId2)//
                    .migrate(processInstanceId1);

        }
    }

    @Test
    public void getUserTask() {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId1);
        Process process = bpmnModel.getMainProcess();
        List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
        for (UserTask userTask : userTasks) {
            System.out.println(userTask.getName());
        }
    }

    // 10.1. 简单示例
    // 让我们从一个简单的示例开始, 解释流动引擎中流程实例迁移的基础知识。在这个简单的示例中, 我们使用以下用例:
    // 有一个进程实例运行的进程定义具有名为 simpleTasks 的密钥, 它由启动事件-用户任务1结束事件组成。
    // 正在运行的进程实例具有用户任务1的活动状态。
    // 使用相同的密钥 (simpleTasks) 部署新的进程定义, 流程定义现在由启动事件-用户任务 1-用户任务 2-结束事件组成。
    // 正在运行的进程实例应迁移到新的进程定义。
    // 若要测试进程实例是否可以迁移而不出现问题, 可以使用以下代码:
    // ProcessInstanceMigrationValidationResult 对象 = runtimeService.
    // createProcessInstanceMigrationBuilder ()
    // . migrateToProcessDefinition (version2ProcessDef. getId ())
    // . validateMigration (processInstanceToMigrate. getId ());
    //
    // 布尔 isMigrationValid = 对象. isMigrationValid ();
    // 流程实例迁移生成器可用于验证, 如稍后所示, 将迁移一个或多个进程实例。在这种情况下,
    // 我们测试是否可以将正在运行的进程实例迁移到具有2用户任务的新进程定义版本。由于两个进程定义版本之间没有更改用户任务 id, 因此可以迁移进程实例, 而无需任何其他映射配置。因此, 迁移将具有
    // true 的迁移有效布尔值。这意味着我们可以运行实际迁移, 而不会出现预期问题。
    // ProcessInstanceMigrationValidationResult 对象 = runtimeService.
    // createProcessInstanceMigrationBuilder ()
    // . migrateToProcessDefinition (version2ProcessDef. getId ())
    // . 迁移 (processInstanceToMigrate getId ());
    // 执行迁移方法后, 正在运行的进程实例将迁移到新的进程定义 (包括历史信息)。这意味着, 当用户任务1完成时, 用户任务2将成为正在运行的进程实例的下一个活动状态。
    //
    // 10.2. 迁移与活动迁移映射
    // 在简单示例中, 用户任务1自动映射到新流程定义版本中的同一用户任务。但在某些情况下, 正在运行的进程实例的当前活动不再存在于新的流程定义中, 或者由于另一个原因,
    // 该活动应迁移到其他活动。对于此用例, 流程实例迁移生成器允许您指定特定活动迁移映射的列表。
    //
    // ProcessInstanceMigrationValidationResult 对象 = runtimeService.
    // createProcessInstanceMigrationBuilder ()
    // . migrateToProcessDefinition (version2ProcessDef. getId ())
    // . addActivityMigrationMapping ("userTask1Id", "userTask2Id")
    // . 迁移 (processInstanceToMigrate getId ());
    // 在此示例中, 运行具有活动状态的用户任务1的进程实例将迁移到具有2个用户任务的新进程定义版本, 并且活动状态将迁移到用户任务2。这意味着, 当用户任务2完成时, 流程实例将结束。
    //
    // 10.3. 支持的流程实例迁移案例
    // 本节概述了流程实例迁移所支持的案例。如果您要查找的案例尚未得到支持, 请查看下一节中的后续支持。
    //
    // 将等待状态 (用户任务、接收任务、中间捕获事件) 自动迁移到新流程定义版本中具有相同 id 的活动。
    // 通过在正在运行的进程实例中指定特定活动状态的目标活动, 手动迁移等待状态。
    // 将等待状态迁移到具有边界计时器、信号或消息事件的活动。
    // 将边界计时器、信号或消息事件的等待状态迁移到没有边界事件的活动。
    // 将等待状态迁移到嵌入的 sub 进程或嵌套嵌入子流程中的活动。
    // 将嵌入的 sub 进程或嵌套嵌入 sub 进程中的等待状态迁移到进程定义或另一个嵌套作用域的根级别。
    // 将等待状态迁移到 (嵌套) 事件子进程中的活动, 既中断又不中断。
    //
    // 10.4. 即将进行的流程实例迁移支持
    // 使用此版本的流动性引擎, 将添加流程实例迁移支持的第一步。在下一版本中, 重点是添加对以下迁移案例的支持:
    //
    // 支持在使用并行或非独占网关时移动多个执行, 以便在网关范围以外的一项实施。
    // 支持从单个执行移动到并行或包含网关中的多个执行。
    // 支持将多实例执行的集合移动到另一个活动。
    // 支持将等待状态移动到多实例活动。
    // 当进程定义中存在一个或多个调用活动时, 支持将等待状态移动到子进程中的活动。
    // 支持将等待状态移动到父进程中的活动。
    // 支持在流程实例或本地执行范围中添加和删除变量。
    // 支持定义目标用户任务的分配规则和其他配置选项。

}
