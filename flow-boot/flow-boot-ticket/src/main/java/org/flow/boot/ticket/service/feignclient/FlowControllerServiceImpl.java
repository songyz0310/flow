package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.springframework.stereotype.Service;

@Service
public class FlowControllerServiceImpl implements FlowControllerService {

	public Response list(EntityType entityType) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

}
