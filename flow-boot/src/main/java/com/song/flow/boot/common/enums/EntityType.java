package com.song.flow.boot.common.enums;

public enum EntityType {

	TICKET("工单"),//
	;

	EntityType(String name) {
		this.name = name;
	};

	private String name;

	public String getName() {
		return name;
	}

}
