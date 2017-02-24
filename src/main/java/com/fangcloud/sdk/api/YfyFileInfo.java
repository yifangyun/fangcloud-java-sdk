package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFileInfo extends YfyItemInfo {
    @JsonProperty("comments_count")
    private long commentsCount;
    @JsonProperty("extension_category")
    private String extensionCategory;

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getExtensionCategory() {
        return extensionCategory;
    }

    public void setExtensionCategory(String extensionCategory) {
        this.extensionCategory = extensionCategory;
    }
}
