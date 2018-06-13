package org.flow.boot.ticket.service;

import org.flow.boot.ticket.form.TicketForm;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

	public void openTicket(TicketForm ticket) {
		throw new RuntimeException("我错了");
	}

}
