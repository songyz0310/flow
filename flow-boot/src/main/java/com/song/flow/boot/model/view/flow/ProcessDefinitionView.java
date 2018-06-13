package com.song.flow.boot.model.view.flow;

/**
 * 流程定义
 * 
 * @author songyz
 *
 */
public class ProcessDefinitionView {

	private String id;
	private String category;
	private String name;
	private String key;
	private String description;
	private int version;
	private String resourceName;
	private String deploymentId;
	private String diagramResourceName;
	private boolean startFormKey;
	private boolean graphicalNotation;
	private boolean suspended;
	private String tenantId;
	private String derivedFrom;
	private String derivedFromRoot;
	private int derivedVersion;
	private String engineVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDiagramResourceName() {
		return diagramResourceName;
	}

	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}

	public boolean isStartFormKey() {
		return startFormKey;
	}

	public void setStartFormKey(boolean startFormKey) {
		this.startFormKey = startFormKey;
	}

	public boolean isGraphicalNotation() {
		return graphicalNotation;
	}

	public void setGraphicalNotation(boolean graphicalNotation) {
		this.graphicalNotation = graphicalNotation;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public int getDerivedVersion() {
		return derivedVersion;
	}

	public void setDerivedVersion(int derivedVersion) {
		this.derivedVersion = derivedVersion;
	}

	public String getEngineVersion() {
		return engineVersion;
	}

	public void setEngineVersion(String engineVersion) {
		this.engineVersion = engineVersion;
	}

}
