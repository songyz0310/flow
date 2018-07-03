package org.flow.boot.common.vo.process;

import org.flow.boot.common.enums.StepType;

/**
 * 流程步骤
 * 
 * @author songyz
 *
 */
public class FlowStepVO {

	private String stepId;

	private String processId;

	private String stepName;

	private int stepRank;

	private StepType stepType;

	private String pageId;

	private FlowStepExtenseVO flowStepExtense;

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
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

	public int getStepRank() {
		return stepRank;
	}

	public void setStepRank(int stepRank) {
		this.stepRank = stepRank;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public FlowStepExtenseVO getFlowStepExtense() {
		return flowStepExtense;
	}

	public void setFlowStepExtense(FlowStepExtenseVO flowStepExtense) {
		this.flowStepExtense = flowStepExtense;
	}

}
