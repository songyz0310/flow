package org.flow.boot.ticket.service.feignclient;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.vo.process.FlowPageConfigVO;
import org.springframework.stereotype.Service;

@Service
public class FlowPageControllerServiceImpl implements FlowPageControllerService {

	public Response<FlowPageConfigVO> queryById(String configId) {
		return Response.errorResponse(ErrorCode.UNKNOWN);
	}

}
