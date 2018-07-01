package org.flow.boot.process.web.test;

import org.flow.boot.common.Response;
import org.flow.boot.process.service.IRuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow/instance")
public class InstanceController {

	@Autowired
	private IRuntimeService iRuntimeService;

	@GetMapping("/start/{processDefinitionId}")
	public Response<?> query(@PathVariable String processDefinitionId) {
		return Response.okResponse(iRuntimeService.startFlowInstanceById(processDefinitionId));
	}

	@GetMapping("/test/list")
	public Response<?> test(String entityId) {

		return Response.okResponse(iRuntimeService.queryInstanceList());
	}
	@GetMapping("/test/activity")
	public Response<?> activity(String processInstanceId) {
		return Response.okResponse(iRuntimeService.queryActivityList(processInstanceId));
	}

}
