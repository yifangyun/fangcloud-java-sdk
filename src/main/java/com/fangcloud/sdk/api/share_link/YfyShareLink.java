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
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("modified_at")
    private Long modifiedAt;
    @JsonProperty("due_time")
    private String dueTime;
    @JsonProperty("disable_download")
    private Boolean disableDownload;
    @JsonProperty("download_count_total")
    private Long downloadCountTotal;
    @JsonProperty("view_count")
    private Long viewCount;

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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Long modifiedAt) {
        this.modifiedAt = modifiedAt;
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

    public Long getDownloadCountTotal() {
        return downloadCountTotal;
    }

    public void setDownloadCountTotal(Long downloadCountTotal) {
        this.downloadCountTotal = downloadCountTotal;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}
