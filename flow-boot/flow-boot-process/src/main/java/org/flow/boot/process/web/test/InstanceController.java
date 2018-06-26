package org.flow.boot.process.web.test;

import java.util.LinkedList;
import java.util.List;

import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.ExecutionVO;
import org.flow.boot.process.entity.FlowInstance;
import org.flow.boot.process.repository.FlowInstanceRepository;
import org.flow.boot.process.service.IRuntimeService;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.BeanUtils;
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
	@Autowired
	private FlowInstanceRepository flowInstanceRepository;
	@Autowired
	private RuntimeService runtimeService;

	@GetMapping("/start/{processDefinitionId}")
	public Response<?> query(@PathVariable String processDefinitionId) {
		return Response.okResponse(iRuntimeService.startFlowInstanceById(processDefinitionId));
	}

	@GetMapping("/test/execution")
	public Response<?> test(String entityId) {

		List<FlowInstance> flowInstances = flowInstanceRepository.findByEntityTypeAndEntityId(EntityType.TICKET,
				entityId);
		String instanceId = flowInstances.get(0).getInstanceId();

		List<ExecutionVO> data = new LinkedList<>();
		
		
		runtimeService.createExecutionQuery().processInstanceId(instanceId).list().forEach(ex -> {
			ExecutionVO vo = new ExecutionVO();
			BeanUtils.copyProperties(ex, vo);
			data.add(vo);
		});

		return Response.okResponse(data);
	}

}
