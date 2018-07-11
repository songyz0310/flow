package org.flow.boot.common.vo.process.test;

public class FormInfoVO {

	protected String id;
	protected String name;
	protected String description;
	protected String key;
	protected int version;
	protected FormModelVO formModel;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public FormModelVO getFormModel() {
		return formModel;
	}

	public void setFormModel(FormModelVO formModel) {
		this.formModel = formModel;
	}

}
