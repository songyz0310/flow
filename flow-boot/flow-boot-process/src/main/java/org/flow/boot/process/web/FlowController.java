package org.flow.boot.process.web;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.process.form.ProcessForm;
import org.flow.boot.process.repository.FlowProcessRepository;
import org.flow.boot.process.service.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("flow")
public class FlowController {

	@Autowired
	private IProcessService iProcessService;
	@Autowired
	private FlowProcessRepository flowProcessRepository;

	@PostMapping(value = "deploy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response deploy(@RequestParam(required = false) MultipartFile file, String name, String key)
			throws IOException {
		if (Objects.isNull(file) || StringUtils.isAnyEmpty(name, key)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		ProcessForm form = new ProcessForm();
		form.setKey(key);
		form.setName(name);
		form.setFile(file.getInputStream());
		iProcessService.deploy(form);
		return Response.okResponse("成功");
	}

	@GetMapping(value = "/list")
	public Response list(EntityType entityType) throws IOException {
		if (Objects.isNull(entityType)) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		return Response.okResponse(flowProcessRepository.findByEntityType(entityType));
	}

}
