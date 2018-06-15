package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.common.vo.process.FlowProcessVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "process", fallback = FlowControllerServiceImpl.class, path = "flow")
public interface FlowControllerService {

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Response<?> list(@RequestParam("entityType") EntityType entityType);

	@RequestMapping(value = "query/{processId}", method = RequestMethod.GET)
	public Response<FlowProcessVO> findById(@PathVariable("processId") String processId);

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public Response<FlowInstanceVO> start(//
			@RequestParam("entityType") EntityType entityType, //
			@RequestParam("processId") String processId, //
			@RequestParam("entityId") String entityId);

	@RequestMapping(value = "rendered/html", method = RequestMethod.GET)
	public Response<String> getRenderedHtml(//
			@RequestParam("entityType") EntityType entityType, //
			@RequestParam("entityId") String entityId);

	@RequestMapping(value = "complete", method = RequestMethod.POST)
	public Response<FlowInstanceVO> completeStep(//
			@RequestParam("entityType") EntityType entityType, //
			@RequestParam("entityId") String entityId);

}
