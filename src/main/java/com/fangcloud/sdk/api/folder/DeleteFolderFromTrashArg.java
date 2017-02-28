package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteFolderFromTrashArg implements YfyArg {
    @JsonProperty("folder_ids")
    private List<Long> folderIds;
    @JsonProperty("clear_trash")
    private boolean clearTrash;

    public DeleteFolderFromTrashArg(List<Long> folderIds, boolean clearTrash) {
        this.folderIds = folderIds;
        this.clearTrash = clearTrash;
    }

    public List<Long> getFolderIds() {
        return folderIds;
    }

    public void setFolderIds(List<Long> folderIds) {
        this.folderIds = folderIds;
    }

    public boolean isClearTrash() {
        return clearTrash;
    }

    public void setClearTrash(boolean clearTrash) {
        this.clearTrash = clearTrash;
    }
}
