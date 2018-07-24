package org.flow.boot.db.dto;

import java.util.Objects;

public class StepJumpDTO extends StepDTO {

    private String jumpToStepId;

    public boolean paramIsMiss() {
        return super.paramIsMiss() || Objects.equals(jumpToStepId, "");
    }

    public String getJumpToStepId() {
        return jumpToStepId;
    }

    public void setJumpToStepId(String jumpToStepId) {
        this.jumpToStepId = jumpToStepId;
    }

}
