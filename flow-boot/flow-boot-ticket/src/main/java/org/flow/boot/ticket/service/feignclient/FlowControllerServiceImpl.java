package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.common.vo.process.FlowProcessVO;
import org.springframework.stereotype.Service;

@Service
public class FlowControllerServiceImpl implements FlowControllerService {

	public Response<?> list(EntityType entityType) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<FlowInstanceVO> start(EntityType entityType, String processId, String entityId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<FlowProcessVO> queryById(String processId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<String> getRenderedHtml(EntityType entityType, String entityId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<FlowInstanceVO> completeStep(EntityType ticket, String ticketId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<FlowInstanceVO> cancelStep(EntityType entityType, String entityId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

	public Response<FlowInstanceVO> jumpStep(EntityType entityType, String entityId, String stepId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

}
