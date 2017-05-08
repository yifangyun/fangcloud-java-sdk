package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CopyFileArg implements YfyArg {
    @JsonProperty("target_folder_id")
    private long targetFolderId;

    public CopyFileArg(long targetFolderId) {
        this.targetFolderId = targetFolderId;
    }

    public long getTargetFolderId() {
        return targetFolderId;
    }

    public void setTargetFolderId(long targetFolderId) {
        this.targetFolderId = targetFolderId;
    }
}
