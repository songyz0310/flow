package org.flow.boot.common.dto.ticket;

import java.util.Objects;

import org.flow.boot.common.dto.ABaseDTO;
import org.flow.boot.common.enums.EntityType;

public class StepDTO extends ABaseDTO {

	protected EntityType entityType;

	protected String entityId;

	protected String stepId;

	public boolean paramIsMiss() {
		return Objects.isNull(entityType) || Objects.equals(entityId, "") || Objects.equals(stepId, "");
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

}
