package com.fangcloud.core.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFolderInfo {
    @JsonProperty("item_count")
    private long itemCount;

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }
}
