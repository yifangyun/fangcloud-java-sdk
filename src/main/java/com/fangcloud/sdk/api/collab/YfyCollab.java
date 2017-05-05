package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyCollab extends YfyBaseDTO {
    @JsonProperty("collab_id")
    private Long collabId;
    private YfyMiniUser user;
    private Boolean accepted;
    private String role;

    public Long getCollabId() {
        return collabId;
    }

    public void setCollabId(Long collabId) {
        this.collabId = collabId;
    }

    public YfyMiniUser getUser() {
        return user;
    }

    public void setUser(YfyMiniUser user) {
        this.user = user;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
