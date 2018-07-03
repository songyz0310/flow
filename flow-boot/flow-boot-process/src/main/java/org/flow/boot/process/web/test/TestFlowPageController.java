package org.flow.boot.process.web.test;

import org.flow.boot.common.Response;
import org.flow.boot.process.repository.FlowPageRepository;
import org.flow.boot.process.service.test.TestFlowPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/flow/page")
public class TestFlowPageController {

	@Autowired
	private TestFlowPageService testFlowPageService;
	@Autowired
	private FlowPageRepository flowPageRepository;

	@GetMapping("list")
	public Response<?> queryList() {
		return Response.okResponse(flowPageRepository.findAll());
	}

	@GetMapping("init")
	public Response<?> initData() {
		if (flowPageRepository.findAll().size() == 0) {
			testFlowPageService.initData();
			testFlowPageService.initPage();
			return queryList();
		} else {
			return Response.okResponse("禁止重复初始化");
		}
	}

}
