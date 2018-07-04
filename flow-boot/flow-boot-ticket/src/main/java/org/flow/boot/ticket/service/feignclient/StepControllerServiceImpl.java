package org.flow.boot.ticket.service.feignclient;

import java.util.ArrayList;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.vo.process.FlowStepExtenseVO;
import org.flow.boot.common.vo.process.FlowStepVO;
import org.springframework.stereotype.Service;

@Service
public class StepControllerServiceImpl implements StepControllerService {

	public Response<FlowStepVO> queryById(String stepId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<FlowStepExtenseVO> queryByStepStatus(String stepStatus) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<ArrayList<FlowStepExtenseVO>> queryByStepStatus(String processId, String toStatus) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

}
