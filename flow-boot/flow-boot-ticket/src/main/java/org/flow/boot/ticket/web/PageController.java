package org.flow.boot.ticket.web;

import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.ticket.service.feignclient.FlowControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

	@Autowired
	private FlowControllerService flowControllerService;

	@GetMapping("ticket")
	public String ticket(ModelMap map) {
		Response list = flowControllerService.list(EntityType.TICKET);
		map.put("list", list);
		return "ticket";

	}

}
