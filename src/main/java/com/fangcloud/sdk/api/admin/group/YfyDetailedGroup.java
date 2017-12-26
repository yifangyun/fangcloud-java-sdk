package com.fangcloud.sdk.api.admin.group;

import com.fangcloud.sdk.api.group.YfyGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyDetailedGroup extends YfyGroup {
    @JsonProperty("collab_auto_accepted")
    private Boolean collabAutoAccepted;
    @JsonProperty("is_visible")
    private Boolean isVisible;

    public Boolean getCollabAutoAccepted() {
        return collabAutoAccepted;
    }

    public void setCollabAutoAccepted(Boolean collabAutoAccepted) {
        this.collabAutoAccepted = collabAutoAccepted;
    }

    public Boolean getVisiable() {
        return isVisible;
    }

    public void setVisiable(Boolean visiable) {
        isVisible = visiable;
    }
}
