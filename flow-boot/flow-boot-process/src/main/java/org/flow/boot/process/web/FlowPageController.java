package org.flow.boot.process.web;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.repository.FlowPageRepository;
import org.flow.boot.process.service.FlowPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flow/page")
public class FlowPageController {

	@Autowired
	private FlowPageRepository flowPageRepository;
	@Autowired
	private FlowPageService flowPageService;

	@GetMapping("list")
	public Response queryList() {
		return Response.okResponse(flowPageRepository.findAll());
	}

	@GetMapping("init")
	public Response initPage(String pageId) {
		if (StringUtils.isAnyEmpty(pageId)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		flowPageService.createPage(pageId);
		return Response.okResponse();
	}

}
