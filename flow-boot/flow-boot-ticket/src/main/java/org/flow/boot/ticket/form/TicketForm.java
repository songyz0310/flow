package org.flow.boot.ticket.form;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.BaseForm;
import org.flow.boot.common.enums.ticket.TicketStatus;
import org.flow.boot.common.enums.ticket.TicketType;

public class TicketForm extends BaseForm {

	private String ticketId;
	private String processId;
	private String processName;
	private String stepId;
	private String stepName;
	private TicketStatus status;
	private TicketType type;

	private Map<String, String> page;
	private Map<String, String> pagefile;

	public boolean paramIsMiss() {
		return StringUtils.isAnyEmpty(processId);
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

	public Map<String, String> getPage() {
		return page;
	}

	public void setPage(Map<String, String> page) {
		this.page = page;
	}

	public Map<String, String> getPagefile() {
		return pagefile;
	}

	public void setPagefile(Map<String, String> pagefile) {
		this.pagefile = pagefile;
	}

}
