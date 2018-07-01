package org.flow.boot.process.web;

import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.repository.FlowPageConfigRepository;
import org.flow.boot.process.repository.FlowPageRepository;
import org.flow.boot.process.service.FlowPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flow/page")
public class FlowPageController {

	@Autowired
	private FlowPageService flowPageService;
	@Autowired
	private FlowPageRepository flowPageRepository;
	@Autowired
	private FlowPageConfigRepository flowPageConfigRepository;

	@GetMapping("list")
	public Response<?> queryList() {
		return Response.okResponse(flowPageRepository.findAll());
	}

	@GetMapping("init")
	public Response<?> initData() {
		if (flowPageRepository.findAll().size() == 0) {
			flowPageService.initData();
			flowPageService.initPage();
			return Response.okResponse("初始化成功");
		} else {
			return Response.okResponse("禁止重复初始化");
		}
	}

	@GetMapping(value = "query/{configId}")
	public Response<?> findById(@PathVariable String configId) {
		if (Objects.isNull(configId)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		return Response.okResponse(flowPageConfigRepository.findOne(configId));
	}

}
