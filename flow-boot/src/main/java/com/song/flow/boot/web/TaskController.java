package com.song.flow.boot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song.flow.boot.common.Response;
import com.song.flow.boot.service.ITaskService;

@RestController
@RequestMapping("/flow/task")
public class TaskController {

	@Autowired
	private ITaskService iTaskService;

	@GetMapping("/query/{taskId}")
	public Response query(@PathVariable String taskId) {
		return Response.okResponse(iTaskService.queryById(taskId));
	}

	@GetMapping("/query/list")
	public Response queryList() {
		return Response.okResponse(iTaskService.queryList());
	}

	@GetMapping("/formmodel/{taskId}")
	public Response queryFormModel(@PathVariable String taskId) {
		return Response.okResponse(iTaskService.queryFormInfo(taskId));
	}

	@GetMapping(value = "/form/{taskId}", produces = MediaType.TEXT_HTML_VALUE)
	public Object queryForm(@PathVariable String taskId) {
		return iTaskService.queryForm(taskId);
	}

}
