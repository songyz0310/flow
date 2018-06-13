package org.flow.boot.process.view;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DeploymentView {

	private String id;
	private String name;
	@JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deploymentTime;
	private String category;
	private String key;
	private String derivedFrom;
	private String derivedFromRoot;
	private String tenantId;
	private String engineVersion;
	private boolean isNew;
	// private Map<String, Object> resources;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDeploymentTime() {
		return deploymentTime;
	}

	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDerivedFrom() {
		return derivedFrom;
	}

	public void setDerivedFrom(String derivedFrom) {
		this.derivedFrom = derivedFrom;
	}

	public String getDerivedFromRoot() {
		return derivedFromRoot;
	}

	public void setDerivedFromRoot(String derivedFromRoot) {
		this.derivedFromRoot = derivedFromRoot;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public void setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	// public Map<String, Object> getResources() {
	// return resources;
	// }
	//
	// public void setResources(Map<String, Object> resources) {
	// this.resources = resources;
	// }

}
