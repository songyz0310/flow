package org.flow.boot.process.web;

import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.repository.FlowPageConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flow/page")
public class FlowPageController {

	@Autowired
	private FlowPageConfigRepository flowPageConfigRepository;

	@GetMapping("query/{configId}")
	public Response<?> findById(@PathVariable String configId) {
		if (Objects.isNull(configId))
			return Response.errorResponse(ErrorCode.PARAM_MISS);

		return Response.okResponse(flowPageConfigRepository.getOne(configId));
	}

}
