package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListCollabResult extends YfyBaseDTO {
    @JsonProperty("final_role")
    private String finalRole;
    @JsonProperty("collab_info")
    private List<YfyCollab> collabInfo;

    public String getFinalRole() {
        return finalRole;
    }

    public void setFinalRole(String finalRole) {
        this.finalRole = finalRole;
    }

    public List<YfyCollab> getCollabInfo() {
        return collabInfo;
    }

    public void setCollabInfo(List<YfyCollab> collabInfo) {
        this.collabInfo = collabInfo;
    }
}
