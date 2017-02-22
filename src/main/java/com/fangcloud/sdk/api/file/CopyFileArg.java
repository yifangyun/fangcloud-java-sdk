package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CopyFileArg implements YfyArg {
    @JsonProperty("file_id")
    private long fileId;
    @JsonProperty("target_folder_id")
    private long targetFolderId;

    public CopyFileArg() {}

    public CopyFileArg(long fileId, long targetFolderId) {
        this.fileId = fileId;
        this.targetFolderId = targetFolderId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getTargetFolderId() {
        return targetFolderId;
    }

    public void setTargetFolderId(long targetFolderId) {
        this.targetFolderId = targetFolderId;
    }
}
