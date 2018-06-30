package org.flow.boot.common.dto.ticket;

import java.util.Objects;

import org.flow.boot.common.dto.ABaseDTO;
import org.flow.boot.common.enums.EntityType;

public class FlowStepDTO extends ABaseDTO {

	private EntityType entityType;

	private String entityId;

	private String stepId;

	private String address;

	private double longitude;

	private double latitude;

	public boolean paramIsMiss() {
		return Objects.isNull(entityType) || Objects.isNull(entityId) || Objects.isNull(stepId)
				|| Objects.isNull(address) || Objects.equals(entityId, "") || Objects.equals(stepId, "")
				|| Objects.equals(address, "");
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
