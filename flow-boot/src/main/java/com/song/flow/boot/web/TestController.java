package com.song.flow.boot.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song.flow.boot.common.Response;
import com.song.flow.boot.common.entity.FlowProcess;
import com.song.flow.boot.repository.ProcessRepository;

@RestController
@RequestMapping("/flow/test")
public class TestController {

	@Autowired
	private ProcessRepository processRepository;

	@GetMapping("/insert")
	public Response insert(@RequestBody FlowProcess flowProcess) {
		flowProcess.setProcessId(UUID.randomUUID().toString());
		processRepository.save(flowProcess);
		return Response.okResponse(flowProcess);
	}

	@GetMapping("/query/{id}")
	public Response query(@PathVariable String id) {
		return Response.okResponse(processRepository.findById(id));
	}

	@GetMapping("/query/list")
	public Response queryList() {
		return Response.okResponse(processRepository.findAll());
	}

	@GetMapping("/clear")
	public Response clear() {
		processRepository.deleteAll();
		return Response.okResponse(null);
	}

}
