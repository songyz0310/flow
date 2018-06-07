package com.song.flow.boot.web;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.song.flow.boot.common.ErrorCode;
import com.song.flow.boot.common.Response;
import com.song.flow.boot.common.form.FileForm;
import com.song.flow.boot.service.IProcessService;

@RestController
@RequestMapping("/flow/process")
public class ProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

	@Autowired
	private IProcessService iProcessService;

	@PostMapping(value = "/deploy/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response deployZip(@RequestParam(required = false) MultipartFile file, String name, String key) {
		if (Objects.isNull(file) || StringUtils.isAnyEmpty(name, key)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		try {
			FileForm form = new FileForm();
			form.setKey(key);
			form.setName(name);
			form.setFile(file.getInputStream());
			form.setType(FileForm.Type.ZIP);
			iProcessService.deploy(form);
			return Response.okResponse("成功");
		} catch (IOException e) {
			logger.error("流程部署Zip接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

	@PostMapping(value = "/deploy/xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response deployXml(@RequestParam(required = false) MultipartFile file, String name, String key) {
		if (Objects.isNull(file) || StringUtils.isAnyEmpty(name, key)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		try {
			FileForm form = new FileForm();
			form.setKey(key);
			form.setName(name);
			form.setFile(file.getInputStream());
			form.setType(FileForm.Type.XML);
			iProcessService.deploy(form);
			return Response.okResponse("成功");
		} catch (IOException e) {
			logger.error("流程部署Xml接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

	@GetMapping("/query/definition/{processDefinitionId}")
	public Response queryDefinition(@PathVariable String processDefinitionId) {
		return Response.okResponse(iProcessService.queryDefinitionById(processDefinitionId));
	}

	@GetMapping("/query/definition/list")
	public Response queryDefinitionList() {
		return Response.okResponse(iProcessService.queryDefinitionList());
	}

	@GetMapping("/query/deployment/{deploymentId}")
	public Response queryDeployment(@PathVariable String deploymentId) {
		return Response.okResponse(iProcessService.queryDeploymentById(deploymentId));
	}

	@GetMapping("/query/deployment/list")
	public Response queryDeploymentList() {
		return Response.okResponse(iProcessService.queryDeploymentList());
	}

	@GetMapping("/clear")
	public Response clear() {
		iProcessService.clear();
		return Response.okResponse(null);
	}

}
