package org.flow.boot.common.vo.process;

import java.util.Date;

import org.flow.boot.common.enums.EntityType;

/**
 * 流程实例
 * 
 * @author songyz
 *
 */
public class FlowInstanceVO {

	public static enum Status {
		STARTED, // 启动
		RUNNING, // 运行
		STOPED,// 结束
	}

	private String instanceId;

	private String processId;

	protected EntityType entityType;

	protected String entityId;

	private Status status;

	private Date createTime;

	private Date updateTime;

	private String stepId;

	private String entityStatus;

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

}
