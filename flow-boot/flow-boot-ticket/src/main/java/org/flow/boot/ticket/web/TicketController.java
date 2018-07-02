package org.flow.boot.ticket.web;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.dto.ticket.StepActivityDTO;
import org.flow.boot.common.dto.ticket.StepDTO;
import org.flow.boot.common.dto.ticket.StepPageDTO;
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
import org.springframework.web.util.WebUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@RestController
@RequestMapping("ticket")
public class TicketController implements FlowController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private SysTicketRepository sysTicketRepository;

	@Autowired
	@Qualifier("flowTemplateEngine")
	private TemplateEngine templateEngine;

	@PostMapping("open")
	public Response<?> openTicket(@RequestBody TicketForm ticket) {
		if (Objects.isNull(ticket) || ticket.paramIsMiss()) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}

		ticketService.openTicket(ticket);
		return Response.okResponse("成功");
	}

	@GetMapping("list")
	public Response<List<TicketVO>> listTicket() {
		return Response.okResponse(ticketService.ticketList());
	}

	// 流程页面
	public String stepPage(StepDTO step, HttpServletRequest req, HttpServletResponse resp) {
		if (step.paramIsMiss()) {
			return ErrorCode.PARAM_MISS.getMessage();
		}
		SysTicket ticket = sysTicketRepository.findOne(step.getEntityId());
		if (Objects.isNull(ticket)) {
			return "工单不存在";
		} else if (Objects.isNull(ticket.getStepId())) {
			return "工单步骤为空";
		} else if (Objects.equals(ticket.getStepId(), step.getStepId()) == false) {
			return "工单步骤与当前步骤不符";
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE) == false) {
			return "工单步骤不是页面";
		}

		return templateEngine.process(ticketService.getTicketFlowPage(step.getEntityId()),
				new WebContext(req, resp, req.getServletContext()));
	}

	// 流程页面数据
	public Response<?> stepPageData(StepDTO dto) {
		if (dto.paramIsMiss()) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		Response<String> error = Response.errorResponse(ErrorCode.UNKNOWN);
		SysTicket ticket = sysTicketRepository.findOne(dto.getEntityId());
		if (Objects.isNull(ticket)) {
			error.setMessage("工单不存在");
			return error;
		} else if (Objects.equals(ticket.getStepId(), dto.getStepId()) == false) {
			error.setMessage("工单步骤与当前步骤不符");
			return error;
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE) == false) {
			error.setMessage("工单步骤不是页面");
			return error;
		}

		return Response.okResponse(ticketService.getTicketStepData(dto));
	}

	// 流程页面确认
	public Response<?> stepConfirm(StepPageDTO stepPage, HttpServletRequest request) {
		Response<?> response = Response.errorResponse(ErrorCode.UNKNOWN);
		if (stepPage.paramIsMiss()) {
			response.setMessage(ErrorCode.PARAM_MISS.getMessage());
			return response;
		}
		SysTicket ticket = sysTicketRepository.findOne(stepPage.getEntityId());
		if (Objects.isNull(ticket)) {
			response.setMessage("工单不存在");
			return response;
		} else if (Objects.isNull(ticket.getStepId())) {
			response.setMessage("工单步骤为空");
			return response;
		} else if (Objects.equals(ticket.getStepId(), stepPage.getStepId()) == false) {
			response.setMessage("工单步骤与当前步骤不符");
			return response;
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE) == false) {
			response.setMessage("工单步骤不是页面");
			return response;
		}
		Map<String, Object> pageParam = WebUtils.getParametersStartingWith(request, "page_");
		stepPage.setPageParam(pageParam);
		ticketService.stepConfirm(stepPage);
		return Response.okResponse(stepPage);
	}

	// 非页面流程，执行下一步
	public Response<?> stepExecute(StepActivityDTO stepActivity) {
		Response<?> response = Response.errorResponse(ErrorCode.UNKNOWN);
		if (stepActivity.paramIsMiss()) {
			response.setMessage(ErrorCode.PARAM_MISS.getMessage());
			return response;
		}
		SysTicket ticket = sysTicketRepository.findOne(stepActivity.getEntityId());
		if (Objects.isNull(ticket)) {
			response.setMessage("工单不存在");
			return response;
		} else if (Objects.isNull(ticket.getStepId())) {
			response.setMessage("工单步骤为空");
			return response;
		} else if (Objects.equals(ticket.getStepId(), stepActivity.getStepId()) == false) {
			response.setMessage("工单步骤与当前步骤不符");
			return response;
		} else if (Objects.equals(ticket.getStepType(), StepType.PAGE)) {
			response.setMessage("工单步骤是页面");
			return response;
		}

		ticketService.stepExecute(stepActivity);
		return Response.okResponse("成功");
	}

	public Response<?> stepCancel(StepDTO dto) {
		if (dto.paramIsMiss()) {
			return Response.errorResponse(ErrorCode.PARAM_MISS);
		}
		Response<String> error = Response.errorResponse(ErrorCode.UNKNOWN);
		SysTicket ticket = sysTicketRepository.findOne(dto.getEntityId());
		if (Objects.isNull(ticket)) {
			error.setMessage("工单不存在");
			return error;
		} else if (Objects.equals(ticket.getStepId(), dto.getStepId()) == false) {
			error.setMessage("工单步骤与当前步骤不符");
			return error;
		}

		ticketService.stepCancel(dto);
		return Response.okResponse("成功");
	}
}
