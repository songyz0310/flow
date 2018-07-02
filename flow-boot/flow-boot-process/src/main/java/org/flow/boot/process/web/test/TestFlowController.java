package org.flow.boot.process.web.test;

import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.process.repository.FlowProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/flow")
public class TestFlowController {

	@Autowired
	private FlowProcessRepository flowProcessRepository;

	@GetMapping("list")
	public Response<?> list(EntityType entityType) {
		if (Objects.isNull(entityType)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		return Response.okResponse(flowProcessRepository.findByEntityType(entityType));
	}

}
