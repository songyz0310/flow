package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.Response;
import org.flow.boot.common.vo.process.FlowPageConfigVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "process", fallback = FlowPageControllerServiceImpl.class, path = "flow/page")
public interface FlowPageControllerService {

	@RequestMapping(value = "query/{configId}", method = RequestMethod.GET)
	public Response<FlowPageConfigVO> queryById(@PathVariable("configId") String configId);
}
