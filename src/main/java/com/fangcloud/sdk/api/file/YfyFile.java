package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.item.YfyItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFile extends YfyItem {
    private String sha1;
    @JsonProperty("file_version_key")
    private String fileVersionKey;
    @JsonProperty("comments_count")
    private long commentsCount;
    @JsonProperty("extension_category")
    private String extensionCategory;

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getFileVersionKey() {
        return fileVersionKey;
    }

    public void setFileVersionKey(String fileVersionKey) {
        this.fileVersionKey = fileVersionKey;
    }

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
