package org.flow.boot.process.web.test;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.form.FileForm;
import org.flow.boot.process.form.MyForm;
import org.flow.boot.process.service.test.TestFormService;
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

@RestController
@RequestMapping("test/flow/form")
public class TestFormController {

	private static final Logger logger = LoggerFactory.getLogger(TestFormController.class);
	@Autowired
	private TestFormService testFormService;

	@PostMapping(value = "deploy/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response<?> deployForm(@RequestParam(required = false) MultipartFile file, String name, String key,
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
			testFormService.deploy(form);
			return Response.okResponse("成功");
		} catch (IOException e) {
			logger.error("流程部署Xml接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

	@GetMapping("query/{formId}")
	public Response<?> query(@PathVariable String formId) {
		return Response.okResponse(testFormService.queryById(formId));
	}

	@GetMapping("query/list")
	public Response<?> queryList() {
		return Response.okResponse(testFormService.queryList());
	}

	@GetMapping("clear")
	public Response<?> clear() {
		testFormService.clear();
		return Response.okResponse(null);
	}

}
