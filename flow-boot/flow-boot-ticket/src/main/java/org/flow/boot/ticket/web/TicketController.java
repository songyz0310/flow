package org.flow.boot.ticket.web;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.enums.StepType;
import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.entity.SysTicket;
import org.flow.boot.ticket.form.TicketForm;
import org.flow.boot.ticket.repository.SysTicketRepository;
import org.flow.boot.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@RestController
@RequestMapping("ticket")
public class TicketController implements IFlowController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private SysTicketRepository sysTicketRepository;

	@Autowired
	@Qualifier("flowTemplateEngine")
	private TemplateEngine templateEngine;

	@PostMapping(value = "open")
	public Response<?> openTicket(@RequestBody TicketForm ticket) {
		if (Objects.isNull(ticket) || ticket.paramIsMiss()) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		ticketService.openTicket(ticket);
		return Response.okResponse("成功");
	}

	@GetMapping(value = "list")
	public Response<List<TicketVO>> listTicket() {
		return Response.okResponse(ticketService.ticketList());
	}

	// 流程页面
	public String flowPage(String entityId, String stepId, HttpServletRequest req, HttpServletResponse resp) {
		if (Objects.isNull(entityId)) {
			return ErrorCode.PARAM_MISS.getMessage();
		}
		SysTicket ticket = sysTicketRepository.findOne(entityId);
		if (Objects.isNull(ticket)) {
			return "工单不存在";
		} else if (Objects.isNull(ticket.getStepId())) {
			return "工单步骤为空";
		} else if (Objects.equals(ticket.getStepId(), stepId) == false) {
			return "工单步骤与当前步骤不符";
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE) == false) {
			return "工单步骤不是页面";
		}

		WebContext ctx = new WebContext(req, resp, req.getServletContext());
		return templateEngine.process(ticketService.getTicketFlowPage(entityId), ctx);
	}

	// 流程页面数据
	public Response<?> flowPageData(EntityType entityType, String entityId, String stepId) {
		Response<String> error = Response.errorResponse(ErrorCode.UNKNOWN);
		SysTicket ticket = sysTicketRepository.findOne(entityId);
		if (Objects.isNull(ticket)) {
			error.setMessage("工单不存在");
			return error;
		} else if (Objects.equals(ticket.getStepId(), stepId) == false) {
			error.setMessage("工单步骤与当前步骤不符");
			return error;
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE) == false) {
			error.setMessage("工单步骤不是页面");
			return error;
		}
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO 显示加载动画
			e.printStackTrace();
		}
		return Response.okResponse(ticketService.getTicketFlowData(entityType, entityId, stepId));
	}

	// 流程页面确认
	public Response<?> flowConfirm(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();

		return Response.okResponse(parameterMap);
	}

	// 非页面流程，执行下一步
	public Response<?> flowComplete(String entityId) {
		Response<?> response = Response.errorResponse(ErrorCode.UNKNOWN);
		if (Objects.isNull(entityId)) {
			response.setMessage(ErrorCode.PARAM_MISS.getMessage());
			return response;
		}
		SysTicket ticket = sysTicketRepository.findOne(entityId);
		if (Objects.isNull(ticket)) {
			response.setMessage("工单不存在");
			return response;
		} else if (Objects.isNull(ticket.getStepId())) {
			response.setMessage("工单步骤为空");
			return response;
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE)) {
			response.setMessage("工单步骤是页面");
			return response;
		}

		ticketService.completeStep(entityId);
		return Response.okResponse("成功");
	}
}
