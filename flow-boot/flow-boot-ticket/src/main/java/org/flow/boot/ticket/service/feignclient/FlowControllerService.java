package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "process", fallback = FlowControllerServiceImpl.class, path = "flow")
public interface FlowControllerService {

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Response list(@RequestParam("entityType") EntityType entityType);

}
