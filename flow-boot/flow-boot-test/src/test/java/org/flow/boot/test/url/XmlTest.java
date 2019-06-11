package org.flow.boot.test.url;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    private Map<String, Object> variables = new HashMap<>();
    
    
    @Test
    public void testDeploy() {
        String name = "测试URL部署流程";
        String key = "test_key.bpmn";
        String bpmnXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:flowable=\"http://flowable.org/bpmn\" xmlns:i1stcs=\"http://www.1stcs.cn\" targetNamespace=\"http://bpmn.io/schema/bpmn\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\"><bpmn2:process id=\"jpk8DBfBe\" name=\"1\" isExecutable=\"true\" flowable:versionTag=\"kencbCkedt4FfSMS2EcPfPk\" i1stcs:listenerType=\"test\" i1stcs:ssType=\"\"><bpmn2:startEvent id=\"StartEvent_1mh69fh\" name=\"开始\"><bpmn2:outgoing>SequenceFlow_1v68s5f</bpmn2:outgoing></bpmn2:startEvent><bpmn2:userTask id=\"UserTask_1j1kxok\" name=\"任务节点2\" i1stcs:step_icon_path=\"/images/flow/map_iocn.png\"><bpmn2:incoming>SequenceFlow_1dj290t</bpmn2:incoming><bpmn2:outgoing>SequenceFlow_0k4o8c4</bpmn2:outgoing></bpmn2:userTask><bpmn2:endEvent id=\"EndEvent_0a7oles\" name=\"结束\"><bpmn2:incoming>SequenceFlow_0k4o8c4</bpmn2:incoming><bpmn2:incoming>SequenceFlow_1adg4cn</bpmn2:incoming></bpmn2:endEvent><bpmn2:exclusiveGateway id=\"ExclusiveGateway_17p52s0\" name=\"条件判断\"><bpmn2:incoming>SequenceFlow_0dlkntf</bpmn2:incoming><bpmn2:outgoing>SequenceFlow_1dj290t</bpmn2:outgoing><bpmn2:outgoing>SequenceFlow_0isxtwz</bpmn2:outgoing></bpmn2:exclusiveGateway><bpmn2:sequenceFlow id=\"SequenceFlow_1dj290t\" sourceRef=\"ExclusiveGateway_17p52s0\" targetRef=\"UserTask_1j1kxok\" /><bpmn2:userTask id=\"UserTask_13r07tg\" name=\"任务节点4\" i1stcs:step_icon_path=\"/images/flow/map_iocn.png\"><bpmn2:incoming>SequenceFlow_1v68s5f</bpmn2:incoming><bpmn2:outgoing>SequenceFlow_0dlkntf</bpmn2:outgoing></bpmn2:userTask><bpmn2:sequenceFlow id=\"SequenceFlow_1v68s5f\" sourceRef=\"StartEvent_1mh69fh\" targetRef=\"UserTask_13r07tg\" /><bpmn2:sequenceFlow id=\"SequenceFlow_0dlkntf\" sourceRef=\"UserTask_13r07tg\" targetRef=\"ExclusiveGateway_17p52s0\" /><bpmn2:sequenceFlow id=\"SequenceFlow_0k4o8c4\" sourceRef=\"UserTask_1j1kxok\" targetRef=\"EndEvent_0a7oles\" /><bpmn2:userTask id=\"UserTask_1l8eagy\" name=\"任务节点3\" i1stcs:step_icon_path=\"/images/flow/map_iocn.png\"><bpmn2:incoming>SequenceFlow_0isxtwz</bpmn2:incoming><bpmn2:outgoing>SequenceFlow_1adg4cn</bpmn2:outgoing></bpmn2:userTask><bpmn2:sequenceFlow id=\"SequenceFlow_0isxtwz\" sourceRef=\"ExclusiveGateway_17p52s0\" targetRef=\"UserTask_1l8eagy\" /><bpmn2:sequenceFlow id=\"SequenceFlow_1adg4cn\" sourceRef=\"UserTask_1l8eagy\" targetRef=\"EndEvent_0a7oles\" /></bpmn2:process><bpmndi:BPMNDiagram id=\"BPMNDiagram_1\"><bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"jpk8DBfBe\"><bpmndi:BPMNShape id=\"StartEvent_1mh69fh_di\" bpmnElement=\"StartEvent_1mh69fh\"><dc:Bounds x=\"195\" y=\"298\" width=\"36\" height=\"36\" /><bpmndi:BPMNLabel><dc:Bounds x=\"203\" y=\"341\" width=\"22\" height=\"14\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"UserTask_1j1kxok_di\" bpmnElement=\"UserTask_1j1kxok\"><dc:Bounds x=\"536\" y=\"224\" width=\"100\" height=\"80\" /></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"EndEvent_0a7oles_di\" bpmnElement=\"EndEvent_0a7oles\"><dc:Bounds x=\"751\" y=\"298\" width=\"36\" height=\"36\" /><bpmndi:BPMNLabel><dc:Bounds x=\"719\" y=\"309\" width=\"22\" height=\"14\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNShape id=\"ExclusiveGateway_17p52s0_di\" bpmnElement=\"ExclusiveGateway_17p52s0\" isMarkerVisible=\"true\"><dc:Bounds x=\"422\" y=\"291\" width=\"50\" height=\"50\" /><bpmndi:BPMNLabel><dc:Bounds x=\"482\" y=\"309\" width=\"44\" height=\"14\" /></bpmndi:BPMNLabel></bpmndi:BPMNShape><bpmndi:BPMNEdge id=\"SequenceFlow_1dj290t_di\" bpmnElement=\"SequenceFlow_1dj290t\"><di:waypoint x=\"447\" y=\"291\" /><di:waypoint x=\"447\" y=\"264\" /><di:waypoint x=\"536\" y=\"264\" /></bpmndi:BPMNEdge><bpmndi:BPMNShape id=\"UserTask_13r07tg_di\" bpmnElement=\"UserTask_13r07tg\"><dc:Bounds x=\"281\" y=\"276\" width=\"100\" height=\"80\" /></bpmndi:BPMNShape><bpmndi:BPMNEdge id=\"SequenceFlow_1v68s5f_di\" bpmnElement=\"SequenceFlow_1v68s5f\"><di:waypoint x=\"231\" y=\"316\" /><di:waypoint x=\"281\" y=\"316\" /></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"SequenceFlow_0dlkntf_di\" bpmnElement=\"SequenceFlow_0dlkntf\"><di:waypoint x=\"381\" y=\"316\" /><di:waypoint x=\"422\" y=\"316\" /></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"SequenceFlow_0k4o8c4_di\" bpmnElement=\"SequenceFlow_0k4o8c4\"><di:waypoint x=\"636\" y=\"264\" /><di:waypoint x=\"769\" y=\"264\" /><di:waypoint x=\"769\" y=\"298\" /></bpmndi:BPMNEdge><bpmndi:BPMNShape id=\"UserTask_1l8eagy_di\" bpmnElement=\"UserTask_1l8eagy\"><dc:Bounds x=\"536\" y=\"331\" width=\"100\" height=\"80\" /></bpmndi:BPMNShape><bpmndi:BPMNEdge id=\"SequenceFlow_0isxtwz_di\" bpmnElement=\"SequenceFlow_0isxtwz\"><di:waypoint x=\"447\" y=\"341\" /><di:waypoint x=\"447\" y=\"371\" /><di:waypoint x=\"536\" y=\"371\" /></bpmndi:BPMNEdge><bpmndi:BPMNEdge id=\"SequenceFlow_1adg4cn_di\" bpmnElement=\"SequenceFlow_1adg4cn\"><di:waypoint x=\"636\" y=\"371\" /><di:waypoint x=\"769\" y=\"371\" /><di:waypoint x=\"769\" y=\"334\" /></bpmndi:BPMNEdge></bpmndi:BPMNPlane></bpmndi:BPMNDiagram></bpmn2:definitions>";
        
        Deployment deployment = repositoryService.createDeployment()//
                .enableDuplicateFiltering()//
                .key(key).name(key)//
                .addInputStream(key, new ByteArrayInputStream(bpmnXml.getBytes()))//
                .tenantId("songyz")//
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//
                .deploymentId(deployment.getId())//
                .singleResult();
        
        System.out.println(processDefinition.getId());
    }
    
}
