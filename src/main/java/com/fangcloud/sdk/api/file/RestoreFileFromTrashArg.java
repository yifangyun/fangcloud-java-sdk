package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RestoreFileFromTrashArg implements YfyArg {
    @JsonProperty("file_ids")
    private List<Long> fileIds;
    @JsonProperty("restore_all")
    private boolean restoreAll;

    public RestoreFileFromTrashArg(List<Long> fileIds, boolean restoreAll) {
        this.fileIds = fileIds;
        this.restoreAll = restoreAll;
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public boolean isRestoreAll() {
        return restoreAll;
    }

    public void setRestoreAll(boolean restoreAll) {
        this.restoreAll = restoreAll;
    }
}
