package org.flow.boot.process.form;

import java.io.InputStream;

public class ProcessForm {

	private InputStream file;
	private String name;
	private String key;

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
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

}
