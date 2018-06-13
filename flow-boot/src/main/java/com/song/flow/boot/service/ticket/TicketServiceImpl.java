package com.song.flow.boot.service.ticket;

import org.springframework.stereotype.Service;

import com.song.flow.boot.model.form.ticket.TicketForm;

@Service
public class TicketServiceImpl implements TicketService {

	public void openTicket(TicketForm ticket) {
		throw new RuntimeException("我错了");
	}

}
