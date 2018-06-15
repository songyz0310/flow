package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.vo.process.FlowStepVO;
import org.springframework.stereotype.Service;

@Service
public class StepControllerServiceImpl implements StepControllerService {

	public Response<FlowStepVO> findById(String stepId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

}
