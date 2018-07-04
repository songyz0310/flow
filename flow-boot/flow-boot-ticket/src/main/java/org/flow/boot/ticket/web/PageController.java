package org.flow.boot.ticket.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.enums.TicketStatus;
import org.flow.boot.common.vo.process.FlowStepExtenseVO;
import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.service.TicketService;
import org.flow.boot.ticket.service.feignclient.StepControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private StepControllerService stepControllerService;

	private Map<String, String> stepCache = new WeakHashMap<>();

	private String getStepId(String processId, String status) {
		String key = processId + status;
		String stepId = stepCache.get(key);
		if (StringUtils.isEmpty(stepId)) {
			ArrayList<FlowStepExtenseVO> data = stepControllerService.queryByStepStatus(processId, status).getData();
			stepCache.put(key, stepId = data.get(0).getStepId());
		}
		return stepId;
	}

	@GetMapping("index")
	public String index() {
		return "index";
	}

	@GetMapping("ticket/list")
	public String ticketList(ModelMap map) {
		List<TicketVO> ticketList = ticketService.ticketList();
		for (TicketVO ticket : ticketList) {
			String stepName = ticket.getStepName();
			if (Objects.equals(stepName, "接单") || Objects.equals(stepName, "预约")) {
				ticket.setCloseStepId(getStepId(ticket.getProcessId(), TicketStatus.CLOSE.name()));
			} else {
				ticket.setAppointStepId(getStepId(ticket.getProcessId(), TicketStatus.APPOINTED.name()));
				ticket.setCloseStepId(getStepId(ticket.getProcessId(), TicketStatus.CLOSE.name()));
			}
		}
		map.put("ticketList", ticketList);

		map.put("entityType", EntityType.TICKET);
		return "ticketList";
	}

}
