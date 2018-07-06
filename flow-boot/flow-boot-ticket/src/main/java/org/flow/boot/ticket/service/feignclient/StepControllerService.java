package org.flow.boot.ticket.service.feignclient;

import java.util.ArrayList;

import org.flow.boot.common.Response;
import org.flow.boot.common.vo.process.FlowStepExtenseVO;
import org.flow.boot.common.vo.process.FlowStepVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "process", fallback = FlowControllerServiceImpl.class, path = "flow/step")
public interface StepControllerService {

	@RequestMapping(value = "query/{stepId}", method = RequestMethod.GET)
	public Response<FlowStepVO> queryById(@PathVariable("stepId") String stepId);

	@RequestMapping(value = "query/{processId}/{toStatus}", method = RequestMethod.GET)
	public Response<ArrayList<FlowStepExtenseVO>> queryByStepStatus(//
			@PathVariable("processId") String processId, //
			@PathVariable("toStatus") String toStatus);

}
