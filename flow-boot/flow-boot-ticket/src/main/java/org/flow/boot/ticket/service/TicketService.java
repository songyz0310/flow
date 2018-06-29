package org.flow.boot.ticket.service;

import java.util.List;

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.ticket.StepDataVO;
import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.form.TicketForm;

public interface TicketService {

	void openTicket(TicketForm ticket);

	String getTicketFlowPage(String ticketId);

	List<StepDataVO> getTicketFlowData(EntityType entityType, String entityId, String stepId);

	void completeStep(String ticketId);

	List<TicketVO> ticketList();

}
