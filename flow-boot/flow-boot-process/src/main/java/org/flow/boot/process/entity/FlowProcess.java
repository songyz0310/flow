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
 * 流程
 * 
 * @author songyz
 *
 */
@Entity
@Table(name = "flow_process")
public class FlowProcess {

	@Id
	@Column(name = "process_id", length = 64)
	private String processId;

	@Column(name = "process_key", length = 32)
	private String processKey;

	@Column(name = "process_name", length = 32)
	private String processName;

	@Column(name = "file_path", length = 255)
	private String filePath;

	@Column(name = "entity_type", length = 32)
	@Enumerated(EnumType.STRING)
	private EntityType entityType;

	@Column(name = "status", length = 32)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "version")
	private int version;
	
	@Column(name = "tenant_id", length = 32)
	private String tenantId;

	/**************************************/

	public static enum Status {
		ENABLED, // 可用
		DISABLED, // 不可用
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
