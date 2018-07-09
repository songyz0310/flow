package org.flow.boot.process.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

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
	@Column(name = "step_id", length = 32)
	private String stepId;

	@Column(name = "step_icon", length = 255)
	private String stepIcon;

	@Column(name = "from_status", length = 32)
	private String fromStatus;

	@Column(name = "to_status", length = 32)
	private String toStatus;

	@Column(name = "candidate_groups", length = 32)
	private String candidateGroups;

	/**************************************/

	public FlowStepExtense(String stepId, String stepIcon, String fromStatus, String toStatus, String candidateGroups) {
		super();
		this.stepId = stepId;
		this.stepIcon = stepIcon;
		this.fromStatus = fromStatus;
		this.toStatus = toStatus;
		this.candidateGroups = candidateGroups;
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

	public String getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(String candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public void setCandidateGroups(List<String> candidateGroups) {
		if (Objects.nonNull(candidateGroups)) {
			this.candidateGroups = StringUtils.join(candidateGroups.toArray(), ",");
		}
	}

}
