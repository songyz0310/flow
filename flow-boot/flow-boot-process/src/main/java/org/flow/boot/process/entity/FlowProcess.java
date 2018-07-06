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
	@Column(name = "process_id")
	private String processId;

	@Column(name = "process_key")
	private String processKey;

	@Column(name = "process_name")
	private String processName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "entity_type", updatable = false)
	@Enumerated(EnumType.STRING)
	protected EntityType entityType;

	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "version")
	private int version;

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

}
