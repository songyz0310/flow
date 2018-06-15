package org.flow.boot.ticket.service;

import org.flow.boot.ticket.form.TicketForm;

public interface TicketService {

	void openTicket(TicketForm ticket);

	String ticketFlow(String ticketId);

	void completeStep(String ticketId);

}
