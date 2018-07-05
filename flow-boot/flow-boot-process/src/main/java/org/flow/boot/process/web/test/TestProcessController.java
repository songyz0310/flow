package org.flow.boot.process.web.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.process.form.FileForm;
import org.flow.boot.process.service.test.TestFlowService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
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
@RequestMapping("test/flow/process")
public class TestProcessController {

	private static final Logger logger = LoggerFactory.getLogger(TestProcessController.class);

	@Autowired
	private TestFlowService testFlowService;
	@Autowired
	private RepositoryService repositoryService;

	private DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();

	@PostMapping(value = "deploy/zip", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response<?> deployZip(@RequestParam(required = false) MultipartFile file, String name, String key) {
		if (Objects.isNull(file) || StringUtils.isAnyEmpty(name, key)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		try {
			FileForm form = new FileForm();
			form.setKey(key);
			form.setName(name);
			form.setFile(file.getInputStream());
			form.setType(FileForm.Type.ZIP);
			testFlowService.deploy(form);
			return Response.okResponse("成功");
		} catch (IOException e) {
			logger.error("流程部署Zip接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

	@PostMapping(value = "deploy/xml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response<?> deployXml(@RequestParam(required = false) MultipartFile file, String name, String key) {
		if (Objects.isNull(file) || StringUtils.isAnyEmpty(name, key)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		try {
			FileForm form = new FileForm();
			form.setKey(key);
			form.setName(name);
			form.setFile(file.getInputStream());
			form.setType(FileForm.Type.XML);
			testFlowService.deploy(form);
			return Response.okResponse("成功");
		} catch (IOException e) {
			logger.error("流程部署Xml接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

	@GetMapping("query/definition/{processDefinitionId}")
	public Response<?> queryDefinition(@PathVariable String processDefinitionId) {
		return Response.okResponse(testFlowService.queryDefinitionById(processDefinitionId));
	}

	@GetMapping("query/definition/list")
	public Response<?> queryDefinitionList() {
		return Response.okResponse(testFlowService.queryDefinitionList());
	}

	@GetMapping("query/deployment/{deploymentId}")
	public Response<?> queryDeployment(@PathVariable String deploymentId) {
		return Response.okResponse(testFlowService.queryDeploymentById(deploymentId));
	}

	@GetMapping("query/deployment/list")
	public Response<?> queryDeploymentList() {
		return Response.okResponse(testFlowService.queryDeploymentList());
	}

	@GetMapping("query/image/{processDefinitionId}")
	public void queryDeploymentList(@PathVariable String processDefinitionId, HttpServletResponse response) {
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

		try (InputStream in = generator.generateDiagram(bpmnModel, "PNG", Collections.emptyList(),
				Collections.emptyList(), "宋体", "宋体", "宋体", null, 1.0); PrintWriter writer = response.getWriter()) {
			int ch = 0;
			while ((ch = in.read()) != -1)
				writer.write(ch);
		} catch (Exception e) {
			logger.error("生成流程图片异常：", e);
		}

	}

	@GetMapping("clear")
	public Response<?> clear() {
		testFlowService.clear();
		return Response.okResponse(null);
	}

}
