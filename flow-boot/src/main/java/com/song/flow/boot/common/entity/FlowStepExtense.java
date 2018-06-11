package com.song.flow.boot.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程步骤扩展
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_step_extense")
public class FlowStepExtense {

	@Id
	@Column(name = "step_id")
	private String stepId;

	@Column(name = "step_icon")
	private String stepIcon;

	@Column(name = "step_status")
	private String stepStatus;

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepIcon() {
		return stepIcon;
	}

	public void setStepIcon(String stepIcon) {
		this.stepIcon = stepIcon;
	}

	public String getStepStatus() {
		return stepStatus;
	}

	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}

}
