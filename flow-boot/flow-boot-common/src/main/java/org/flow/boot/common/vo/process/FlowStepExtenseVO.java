package org.flow.boot.common.vo.process;

/**
 * 流程步骤扩展
 * 
 * @author songyz
 *
 */
public class FlowStepExtenseVO {

	private String stepId;

	private String stepIcon;

	private String candidateGroups;

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

}
