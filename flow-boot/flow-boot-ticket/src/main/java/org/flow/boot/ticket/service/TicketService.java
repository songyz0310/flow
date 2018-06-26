package org.flow.boot.ticket.service;

import java.util.List;

import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.form.TicketForm;

public interface TicketService {

	void openTicket(TicketForm ticket);

	String ticketFlow(String ticketId);

	void completeStep(String ticketId);

	List<TicketVO> listTicket();

}
