package org.flow.boot.ticket.web;

import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.ticket.form.TicketForm;
import org.flow.boot.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		logger.error("======================");

		ticketService.openTicket(ticket);
		return Response.okResponse("成功");

	}

}
