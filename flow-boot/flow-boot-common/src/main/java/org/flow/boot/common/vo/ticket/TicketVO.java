package org.flow.boot.common.vo.ticket;

import java.util.Date;

import org.flow.boot.common.enums.process.StepType;

public class TicketVO {

	private String ticketId;

	private String processId;

	private String processName;

	private String stepId;

	private String stepName;

	private StepType stepType;

	private String soStatus;

	private String concatPerson;

	private String concatPhone;

	private Date createTime;

	private Date updateTime;

	/********************************************/
	private String appointStepId;

	private String closeStepId;

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

	public String getAppointStepId() {
		return appointStepId;
	}

	public void setAppointStepId(String appointStepId) {
		this.appointStepId = appointStepId;
	}

	public String getCloseStepId() {
		return closeStepId;
	}

	public void setCloseStepId(String closeStepId) {
		this.closeStepId = closeStepId;
	}

}
