package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniElement;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyCollab extends YfyBaseDTO {
    private Long id;
    @JsonProperty("accessible_by")
    private YfyMiniElement accessibleBy;
    private Boolean accepted;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YfyMiniElement getAccessibleBy() {
        return accessibleBy;
    }

    public void setAccessibleBy(YfyMiniElement accessibleBy) {
        this.accessibleBy = accessibleBy;
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
