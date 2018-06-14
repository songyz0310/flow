package org.flow.boot.process.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.StepType;
import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_step")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowStep {

	@Id
	@Column(name = "step_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String stepId;

	@Column(name = "process_id", updatable = false)
	private String processId;

	@Column(name = "step_name")
	private String stepName;

	@Column(name = "step_rank")
	private int stepRank;

	@Column(name = "step_type")
	@Enumerated(EnumType.STRING)
	private StepType stepType;

	@Column(name = "page_id")
	private String pageId;

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

}