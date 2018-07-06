package org.flow.boot.process.web;

import java.util.List;
import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.entity.FlowStepExtense;
import org.flow.boot.process.repository.FlowStepExtenseRepository;
import org.flow.boot.process.repository.FlowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flow/step")
public class StepController {

	@Autowired
	private FlowStepRepository flowStepRepository;
	@Autowired
	private FlowStepExtenseRepository flowStepExtenseRepository;

	@GetMapping("query/{stepId}")
	public Response<?> findById(@PathVariable String stepId) {
		if (Objects.isNull(stepId))
			return Response.errorResponse(ErrorCode.PARAM_MISS);

		return Response.okResponse(flowStepRepository.getOne(stepId));
	}

	@GetMapping("query/{processId}/{toStatus}")
	public Response<?> queryByStepStatus(@PathVariable String processId, @PathVariable String toStatus) {
		if (Objects.isNull(toStatus))
			return Response.errorResponse(ErrorCode.PARAM_MISS);

		List<FlowStepExtense> steps = flowStepExtenseRepository.findByToStatus(processId, toStatus);
		return Response.okResponse(steps);
	}

}
