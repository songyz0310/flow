package org.flow.boot.common.vo.process;

public class FormModelView {

	protected String name;
	protected String key;
	protected int version;
	protected String description;
	protected String outcomeVariableName;

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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOutcomeVariableName() {
		return outcomeVariableName;
	}

	public void setOutcomeVariableName(String outcomeVariableName) {
		this.outcomeVariableName = outcomeVariableName;
	}

}
