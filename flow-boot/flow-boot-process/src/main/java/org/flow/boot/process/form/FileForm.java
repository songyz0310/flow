package org.flow.boot.process.form;

import java.io.InputStream;

public class FileForm {

	public static enum Type {
		ZIP, XML, JSON
	}

	private InputStream file;
	private String name;
	private String key;
	private Type type;

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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
