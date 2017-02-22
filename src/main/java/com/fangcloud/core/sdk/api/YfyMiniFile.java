package com.fangcloud.core.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyMiniFile extends YfyMiniItem {
    private String extension;
    @JsonProperty("created_at")
    private long createdAt;
    @JsonProperty("modified_at")
    private long modifiedAt;
    @JsonProperty("owned_by")
    private YfyMiniUser owner;
    private String description;
    private String remark;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public YfyMiniUser getOwner() {
        return owner;
    }

    public void setOwner(YfyMiniUser owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
