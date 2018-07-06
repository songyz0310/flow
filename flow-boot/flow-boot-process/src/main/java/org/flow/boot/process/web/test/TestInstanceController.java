package org.flow.boot.process.web.test;

import java.util.List;

import org.flow.boot.common.Response;
import org.flow.boot.process.service.test.TestRuntimeService;
import org.flowable.engine.HistoryService;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/flow/instance")
public class TestInstanceController {

	@Autowired
	private TestRuntimeService testRuntimeService;
	@Autowired
	private HistoryService historyService;

	@GetMapping("start/{processDefinitionId}")
	public Response<?> query(@PathVariable String processDefinitionId) {
		return Response.okResponse(testRuntimeService.startFlowInstanceById(processDefinitionId));
	}

	@GetMapping("list")
	public Response<?> test() {
		return Response.okResponse(testRuntimeService.queryInstanceList());
	}

	@GetMapping("activity")
	public Response<?> activity(String processInstanceId) {
		return Response.okResponse(testRuntimeService.queryActivityList(processInstanceId));
	}
	@GetMapping("variables")
	public Response<?> variables(String processInstanceId) {
		
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
		HistoricVariableInstance variableInstance = list.get(0);
		
		return Response.okResponse(variableInstance.getValue());
	}

}
