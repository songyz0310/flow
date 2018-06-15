package org.flow.boot.process.web;

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
	public Response<?> queryList() {
		return Response.okResponse(flowPageRepository.findAll());
	}

	@GetMapping("initPage")
	public Response<?> initPage() {
		flowPageService.initPage();
		return Response.okResponse();
	}

	@GetMapping("initData")
	public Response<?> initData() {
		flowPageService.initData();
		return Response.okResponse();
	}

}
