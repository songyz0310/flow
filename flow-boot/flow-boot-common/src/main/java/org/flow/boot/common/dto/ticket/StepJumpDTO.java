package org.flow.boot.common.dto.ticket;

import java.util.Objects;

public class StepJumpDTO extends StepDTO {

	private String jumpStepId;

	public boolean paramIsMiss() {
		return super.paramIsMiss() || Objects.equals(jumpStepId, "");
	}

	public String getJumpStepId() {
		return jumpStepId;
	}

	public void setJumpStepId(String jumpStepId) {
		this.jumpStepId = jumpStepId;
	}

}
