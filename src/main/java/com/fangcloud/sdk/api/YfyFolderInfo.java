package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFolderInfo extends YfyItemInfo {
    @JsonProperty("item_count")
    private long itemCount;

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }
}
