package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteFolderArg implements YfyArg {
    @JsonProperty("folder_ids")
    private List<Long> folderIds;

    public DeleteFolderArg(List<Long> folderIds) {
        this.folderIds = folderIds;
    }

    public List<Long> getFolderIds() {
        return folderIds;
    }

    public void setFolderIds(List<Long> folderIds) {
        this.folderIds = folderIds;
    }
}
