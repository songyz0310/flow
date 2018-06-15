package org.flow.boot.process.web;

import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.repository.FlowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("step")
public class StepController {

	@Autowired
	private FlowStepRepository flowStepRepository;

	@GetMapping(value = "query/{stepId}")
	public Response<?> findById(@PathVariable String stepId) {
		if (Objects.isNull(stepId)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		return Response.okResponse(flowStepRepository.findOne(stepId));
	}

}
