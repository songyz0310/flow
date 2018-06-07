package com.song.flow.boot.common.view;

import java.util.List;

import org.flowable.form.model.FormField;
import org.flowable.form.model.FormOutcome;

public class FormModelView {

	protected String name;
	protected String key;
	protected int version;
	protected String description;
	protected List<FormField> fields;
	protected List<FormOutcome> outcomes;
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

	public List<FormField> getFields() {
		return fields;
	}

	public void setFields(List<FormField> fields) {
		this.fields = fields;
	}

	public List<FormOutcome> getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(List<FormOutcome> outcomes) {
		this.outcomes = outcomes;
	}

	public String getOutcomeVariableName() {
		return outcomeVariableName;
	}

	public void setOutcomeVariableName(String outcomeVariableName) {
		this.outcomeVariableName = outcomeVariableName;
	}

}
