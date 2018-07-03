package org.flow.boot.process.web.test;

import org.flow.boot.common.Response;
import org.flow.boot.process.service.test.TestTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/flow/task")
public class TestTaskController {

	@Autowired
	private TestTaskService testTaskService;

	@GetMapping("query/{taskId}")
	public Response<?> query(@PathVariable String taskId) {
		return Response.okResponse(testTaskService.queryById(taskId));
	}

	@GetMapping("query/list")
	public Response<?> queryList() {
		return Response.okResponse(testTaskService.queryList());
	}

	@GetMapping("complete/{taskId}")
	public Response<?> complete(@PathVariable String taskId) {
		testTaskService.completeTask(taskId);
		return Response.okResponse();
	}

	@GetMapping("formmodel/{taskId}")
	public Response<?> queryFormModel(@PathVariable String taskId) {
		return Response.okResponse(testTaskService.queryFormInfo(taskId));
	}

	@GetMapping(value = "form/{taskId}", produces = MediaType.TEXT_HTML_VALUE)
	public Object queryForm(@PathVariable String taskId) {
		return testTaskService.queryForm(taskId);
	}

}
