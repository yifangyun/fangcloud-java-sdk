package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListCollabResult extends YfyBaseDTO {
    @JsonProperty("final_role")
    private String finalRole;
    private List<YfyCollab> collabs;

    public String getFinalRole() {
        return finalRole;
    }

    public void setFinalRole(String finalRole) {
        this.finalRole = finalRole;
    }

    public List<YfyCollab> getCollabs() {
        return collabs;
    }

    public void setCollabs(List<YfyCollab> collabs) {
        this.collabs = collabs;
    }
}
