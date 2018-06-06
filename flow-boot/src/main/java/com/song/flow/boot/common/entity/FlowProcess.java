package com.song.flow.boot.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "flow_process")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FlowProcess {

	@Id
	@Column(name = "process_id")
	@GeneratedValue(generator = "jpa-uuid")
	private String processId;

	@Column(name = "process_key")
	private String processKey;

	@Column(name = "process_name")
	private String processName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "create_time", updatable = false)
	private String createTime;

	@Column(name = "update_time")
	private String updateTime;

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

}
