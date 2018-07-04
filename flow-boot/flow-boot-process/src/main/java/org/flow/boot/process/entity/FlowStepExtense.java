package org.flow.boot.process.entity;

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

	@Column(name = "from_status")
	private String fromStatus;

	@Column(name = "to_status")
	private String toStatus;

	public FlowStepExtense(String stepId, String stepIcon, String fromStatus, String toStatus) {
		super();
		this.stepId = stepId;
		this.stepIcon = stepIcon;
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
	}

	public FlowStepExtense() {
		super();
	}

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

	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getToStatus() {
		return toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

}
