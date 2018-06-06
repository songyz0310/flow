package com.song.flow.boot.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.song.flow.boot.common.enums.EntityType;

@Entity
@Table(name = "flow_instance")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowInstance {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(name = "instance_id")
	private String instanceId;

	@Column(name = "process_id", updatable = false)
	private String processId;

	@Column(name = "entity_type", updatable = false)
	@Enumerated(EnumType.ORDINAL)
	private EntityType entityType;

	@Column(name = "entity_id", updatable = false)
	private String entityId;

	@Column(name = "status")
	private String status;

	@Column(name = "create_time", updatable = false)
	private String createTime;

	@Column(name = "update_time")
	private String updateTime;

	@Column(name = "step_key")
	private String stepKey;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getStepKey() {
		return stepKey;
	}

	public void setStepKey(String stepKey) {
		this.stepKey = stepKey;
	}

}
