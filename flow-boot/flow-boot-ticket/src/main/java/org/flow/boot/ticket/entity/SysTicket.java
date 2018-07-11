package org.flow.boot.ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.process.StepType;
import org.flow.boot.common.enums.ticket.TicketStatus;
import org.flow.boot.common.enums.ticket.TicketType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_ticket")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class SysTicket {

	@Id
	@Column(name = "ticket_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String ticketId;

	@Column(name = "process_id")
	private String processId;

	@Column(name = "process_name")
	private String processName;

	@Column(name = "step_id")
	private String stepId;

	@Column(name = "step_name")
	private String stepName;

	@Column(name = "step_type")
	@Enumerated(EnumType.STRING)
	private StepType stepType;

	@Column(name = "so_status")
	@Enumerated(EnumType.STRING)
	private TicketStatus soStatus;

	@Column(name = "concat_person")
	private String concatPerson;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TicketType type;

	@Column(name = "concat_phone")
	private String concatPhone;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

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

	public StepType getStepType() {
		return stepType;
	}

	public void setStepType(StepType stepType) {
		this.stepType = stepType;
	}

	public TicketStatus getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(TicketStatus soStatus) {
		this.soStatus = soStatus;
	}

	public String getConcatPerson() {
		return concatPerson;
	}

	public void setConcatPerson(String concatPerson) {
		this.concatPerson = concatPerson;
	}

	public String getConcatPhone() {
		return concatPhone;
	}

	public void setConcatPhone(String concatPhone) {
		this.concatPhone = concatPhone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

}
