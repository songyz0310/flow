package org.flow.boot.db.dto;

import java.util.Map;

public class StepPageDTO extends StepActivityDTO {

	private Map<String, Object> pageParam;

	public Map<String, Object> getPageParam() {
		return pageParam;
	}

	public void setPageParam(Map<String, Object> pageParam) {
		this.pageParam = pageParam;
	}

}
