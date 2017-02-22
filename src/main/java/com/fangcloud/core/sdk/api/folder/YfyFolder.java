package com.fangcloud.core.sdk.api.folder;

import com.fangcloud.core.sdk.api.item.YfyItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFolder extends YfyItem {
    @JsonProperty("item_count")
    private long itemCount;
    @JsonProperty("folder_type")
    private String folderType;

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }

    public String getFolderType() {
        return folderType;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }
}
