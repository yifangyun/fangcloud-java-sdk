package com.fangcloud.sdk.api.share_link;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyShareLink extends YfyBaseDTO {
    @JsonProperty("unique_name")
    private String uniqueName;
    @JsonProperty("share_link")
    private String shareLink;
    private String access;
    @JsonProperty("password_protected")
    private Boolean passwordProtected;
    @JsonProperty("due_time")
    private String dueTime;
    @JsonProperty("disable_download")
    private Boolean disableDownload;

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Boolean getPasswordProtected() {
        return passwordProtected;
    }

    public void setPasswordProtected(Boolean passwordProtected) {
        this.passwordProtected = passwordProtected;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public Boolean getDisableDownload() {
        return disableDownload;
    }

    public void setDisableDownload(Boolean disableDownload) {
        this.disableDownload = disableDownload;
    }
}
