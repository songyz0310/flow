package org.flow.boot.process.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.flow.boot.common.enums.EntityType;

/**
 * 流程实例
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_instance")
public class FlowInstance {

	public static enum Status {
		STARTED, // 启动
		RUNNING, // 运行
		STOPED,// 结束
	}

	@Id
	@Column(name = "instance_id")
	private String instanceId;

	@Column(name = "process_id", updatable = false)
	private String processId;

	@Column(name = "entity_type", updatable = false)
	@Enumerated(EnumType.STRING)
	protected EntityType entityType;

	@Column(name = "entity_id", updatable = false)
	protected String entityId;

	@Column(name = "entity_status")
	private String entityStatus;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "step_id")
	private String stepId;

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

	public String getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(String entityStatus) {
		this.entityStatus = entityStatus;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

}
