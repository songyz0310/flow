package org.flow.boot.common.vo.process.test;

public class ExecutionVO {

	protected String id;
	protected boolean suspended;
	protected boolean ended;
	protected String activityId;
	protected String processInstanceId;
	protected String parentId;
	protected String superExecutionId;
	protected String rootProcessInstanceId;
	protected String tenantId;
	protected String name;
	protected String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSuperExecutionId() {
		return superExecutionId;
	}

	public void setSuperExecutionId(String superExecutionId) {
		this.superExecutionId = superExecutionId;
	}

	public String getRootProcessInstanceId() {
		return rootProcessInstanceId;
	}

	public void setRootProcessInstanceId(String rootProcessInstanceId) {
		this.rootProcessInstanceId = rootProcessInstanceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
