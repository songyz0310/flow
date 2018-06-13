package org.flow.boot.ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.ValueType;
import org.hibernate.annotations.GenericGenerator;

/**
 * 流程步骤数据
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "sys_flow_step_data")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class SysFlowStepData {

	@Id
	@Column(name = "data_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String dataId;

	@Column(name = "step_id", updatable = false)
	private String stepId;

	@Column(name = "step_name", updatable = false)
	private String stepName;

	@Column(name = "spc_id", updatable = false)
	private String spcId;

	@Column(name = "spc_name", updatable = false)
	private String spcName;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "value", columnDefinition = "TEXT")
	private String value;

	@Column(name = "value_type")
	@Enumerated(EnumType.STRING)
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

	public String getSpcId() {
		return spcId;
	}

	public void setSpcId(String spcId) {
		this.spcId = spcId;
	}

	public String getSpcName() {
		return spcName;
	}

	public void setSpcName(String spcName) {
		this.spcName = spcName;
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
