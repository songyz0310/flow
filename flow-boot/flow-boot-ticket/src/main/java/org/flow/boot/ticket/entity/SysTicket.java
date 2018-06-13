package org.flow.boot.ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.StepType;
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
	private String soStatus;

	@Column(name = "concat_person")
	private double concatPerson;

	@Column(name = "concat_phone")
	private double concatPhone;

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

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public double getConcatPerson() {
		return concatPerson;
	}

	public void setConcatPerson(double concatPerson) {
		this.concatPerson = concatPerson;
	}

	public double getConcatPhone() {
		return concatPhone;
	}

	public void setConcatPhone(double concatPhone) {
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

}
