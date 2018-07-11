package org.flow.boot.common.vo.ticket;

import java.util.Date;

import org.flow.boot.common.enums.process.ValueType;

public class StepDataVO {

	private String dataId;

	private String stepId;

	private String stepName;

	private String fpcId;

	private String fpcName;

	private Date createTime;

	private String value;

	private ValueType valueType;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
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

	public String getFpcId() {
		return fpcId;
	}

	public void setFpcId(String fpcId) {
		this.fpcId = fpcId;
	}

	public String getFpcName() {
		return fpcName;
	}

	public void setFpcName(String fpcName) {
		this.fpcName = fpcName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

}
