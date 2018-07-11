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

	@Column(name = "candidate_groups", length = 32)
	private String candidateGroups;

	/**************************************/

	public FlowStepExtense() {
		super();
	}

	public FlowStepExtense(String stepId, String stepIcon, String candidateGroups) {
		super();
		this.stepId = stepId;
		this.stepIcon = stepIcon;
		this.candidateGroups = candidateGroups;
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
