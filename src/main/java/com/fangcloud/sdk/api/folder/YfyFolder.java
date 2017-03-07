package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.api.YfyItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFolder extends YfyItem {
    @JsonProperty("item_count")
    private Long itemCount;
    @JsonProperty("folder_type")
    private String folderType;

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

    public String getFolderType() {
        return folderType;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }
}
