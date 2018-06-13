package com.song.flow.boot.web.flow;

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
import com.song.flow.boot.model.form.flow.FileForm;
import com.song.flow.boot.model.form.flow.MyForm;
import com.song.flow.boot.service.flow.IFormService;

@RestController
@RequestMapping("/flow/form")
public class FormController {

	private static final Logger logger = LoggerFactory.getLogger(FormController.class);
	@Autowired
	private IFormService iFormService;

	@PostMapping(value = "/deploy/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response deployForm(@RequestParam(required = false) MultipartFile file, String name, String key,
			String parentDeploymentId) {
		if (Objects.isNull(file) || StringUtils.isAnyEmpty(name, key)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		try {
			MyForm form = new MyForm();
			form.setKey(key);
			form.setName(name);
			form.setFile(file.getInputStream());
			form.setType(FileForm.Type.JSON);
			form.setParentDeploymentId(parentDeploymentId);
			iFormService.deploy(form);
			return Response.okResponse("成功");
		} catch (IOException e) {
			logger.error("流程部署Xml接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

	@GetMapping("/query/{formId}")
	public Response query(@PathVariable String formId) {
		return Response.okResponse(iFormService.queryById(formId));
	}

	@GetMapping("/query/list")
	public Response queryList() {
		return Response.okResponse(iFormService.queryList());
	}

	@GetMapping("/clear")
	public Response clear() {
		iFormService.clear();
		return Response.okResponse(null);
	}

}