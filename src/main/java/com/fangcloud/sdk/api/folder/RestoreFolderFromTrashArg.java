package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RestoreFolderFromTrashArg implements YfyArg {
    @JsonProperty("folder_ids")
    private List<Long> folderIds;
    @JsonProperty("restore_all")
    private boolean restoreAll;

    public RestoreFolderFromTrashArg(List<Long> folderIds, boolean restoreAll) {
        this.folderIds = folderIds;
        this.restoreAll = restoreAll;
    }

    public List<Long> getFolderIds() {
        return folderIds;
    }

    public void setFolderIds(List<Long> folderIds) {
        this.folderIds = folderIds;
    }

    public boolean isRestoreAll() {
        return restoreAll;
    }

    public void setRestoreAll(boolean restoreAll) {
        this.restoreAll = restoreAll;
    }
}
