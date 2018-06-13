package com.song.flow.boot.model.view.flow;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 表单部署
 * 
 * @author songyz
 *
 */
public class FormDeploymentView {
	
	private String id;
	private String name;
	@JsonFormat(timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deploymentTime;
	private String category;
	private String tenantId;
	private String parentDeploymentId;

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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getParentDeploymentId() {
		return parentDeploymentId;
	}

	public void setParentDeploymentId(String parentDeploymentId) {
		this.parentDeploymentId = parentDeploymentId;
	}

}
