package org.flow.boot.ticket.web;

import java.util.Objects;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.StepType;
import org.flow.boot.ticket.entity.SysTicket;
import org.flow.boot.ticket.form.TicketForm;
import org.flow.boot.ticket.repository.SysTicketRepository;
import org.flow.boot.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private SysTicketRepository sysTicketRepository;

	@PostMapping(value = "open")
	public Response<?> openTicket(@RequestBody TicketForm ticket) {
		if (Objects.isNull(ticket) || ticket.paramIsMiss()) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		ticketService.openTicket(ticket);
		return Response.okResponse("成功");
	}

	// TODO 未校验
	@GetMapping("flow/page")
	public String ticketFlow(String ticketId) {
		if (Objects.isNull(ticketId)) {
			return ErrorCode.PARAM_MISS.getMessage();
		}
		SysTicket ticket = sysTicketRepository.findOne(ticketId);
		if (Objects.isNull(ticket)) {
			return "工单不存在";
		} else if (Objects.isNull(ticket.getStepId())) {
			return "工单步骤为空";
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE) == false) {
			return "工单步骤不是页面";
		}

		return ticketService.ticketFlow(ticketId);
	}

	@GetMapping("flow/complete")
	public Response<?> complete(String ticketId) {
		Response<?> response = Response.errorResponse(ErrorCode.UNKNOWN);
		if (Objects.isNull(ticketId)) {
			response.setMessage(ErrorCode.UNKNOWN.getMessage());
			return response;
		}
		SysTicket ticket = sysTicketRepository.findOne(ticketId);
		if (Objects.isNull(ticket)) {
			response.setMessage("工单步骤为空");
			return response;
		} else if (Objects.isNull(ticket.getStepId())) {
			return response;
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE)) {
			response.setMessage("工单步骤是页面");
			return response;
		}

		ticketService.completeStep(ticketId);
		return Response.okResponse("成功");
	}
}
