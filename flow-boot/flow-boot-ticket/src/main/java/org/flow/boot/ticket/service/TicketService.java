package org.flow.boot.ticket.service;

import java.util.List;

import org.flow.boot.common.dto.ticket.StepActivityDTO;
import org.flow.boot.common.dto.ticket.StepDTO;
import org.flow.boot.common.dto.ticket.StepPageDTO;
import org.flow.boot.common.vo.ticket.StepDataVO;
import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.form.TicketForm;

public interface TicketService {

	List<TicketVO> ticketList();

	void openTicket(TicketForm ticket);

	String getTicketFlowPage(String ticketId);

	List<StepDataVO> getTicketStepData(StepDTO dto);

	void stepExecute(StepActivityDTO stepActivity);

	void stepConfirm(StepPageDTO stepPage);

	void stepCancel(StepDTO dto);

}
