package com.song.flow.boot.model.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.song.flow.boot.common.enums.EntityType;

/**
 * 实体类型，实体ID
 * 
 * @author songyz
 *
 */
@MappedSuperclass
public class BaseEntityType {

	@Column(name = "entity_type", updatable = false)
	@Enumerated(EnumType.STRING)
	protected EntityType entityType;

	@Column(name = "entity_id", updatable = false)
	protected String entityId;

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

}
