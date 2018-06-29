package org.flow.boot.ticket.web;

import org.flow.boot.ticket.service.TicketService;
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

	@GetMapping("index")
	public String index() {
		return "index";
	}

	@GetMapping("ticket/list")
	public String ticketList(ModelMap map) {
		map.put("ticketList", ticketService.ticketList());
		return "ticketList";
	}

}
