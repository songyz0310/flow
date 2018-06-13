package com.song.flow.boot.web.ticket;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song.flow.boot.common.ErrorCode;
import com.song.flow.boot.common.Response;
import com.song.flow.boot.model.form.ticket.TicketForm;
import com.song.flow.boot.service.ticket.TicketService;

@RestController
@RequestMapping("ticket")
public class TicketController {

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;

	@PostMapping(value = "/open")
	public Response deployForm(@RequestBody TicketForm ticket) {
		if (Objects.isNull(ticket) || ticket.paramIsMiss()) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		try {
			ticketService.openTicket(ticket);
			return Response.okResponse("成功");
		} catch (Exception e) {
			logger.error("流程部署Xml接口异常，exception:{}", e);
			return Response.errorResponse(ErrorCode.UNKNOWN);
		}
	}

}
