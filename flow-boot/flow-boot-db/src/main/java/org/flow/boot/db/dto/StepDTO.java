package org.flow.boot.db.dto;

import java.util.Objects;

import org.flow.boot.common.dto.ABaseDTO;

public class StepDTO extends ABaseDTO {

    protected Integer entityType;

    protected String entityId;

    protected String stepId;

    protected Integer tenantId;

    public boolean paramIsMiss() {
        return Objects.isNull(entityType) || Objects.equals(entityId, "") || Objects.equals(stepId, "");
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
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

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

}
