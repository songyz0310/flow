package org.flow.boot.ticket.web;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.flow.boot.common.enums.StepType;
import org.flow.boot.common.vo.ticket.TicketVO;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@RestController
@RequestMapping("ticket")
public class TicketController {

	private TemplateEngine templateEngine = new TemplateEngine();
	{
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

		templateResolver.setTemplateMode("LEGACYHTML5");
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);

		templateEngine.setTemplateResolver(templateResolver); // 设置模板解析器
	}

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

	@GetMapping(value = "list")
	public Response<List<TicketVO>> listTicket() {
		return Response.okResponse(ticketService.listTicket());
	}

	@GetMapping(value = "flow/page")
	public String ticketFlow(String ticketId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

		WebContext ctx = new WebContext(req, resp, req.getServletContext());
		return templateEngine.process(ticketService.ticketFlow(ticketId), ctx);
	}

	@GetMapping(value = "flow/complete")
	public Response<?> complete(String ticketId) {
		Response<?> response = Response.errorResponse(ErrorCode.UNKNOWN);
		if (Objects.isNull(ticketId)) {
			response.setMessage(ErrorCode.PARAM_MISS.getMessage());
			return response;
		}
		SysTicket ticket = sysTicketRepository.findOne(ticketId);
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

		ticketService.completeStep(ticketId);
		return Response.okResponse("成功");
	}
}
