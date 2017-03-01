package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MoveFileArg implements YfyArg {
    @JsonProperty("file_ids")
    private List<Long> fileIds;
    @JsonProperty("target_folder_id")
    private long targetFolderId;

    public MoveFileArg(List<Long> fileIds, long targetFolderId) throws ClientValidationException {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new ClientValidationException("file ids can not be null or be empty");
        }
        this.fileIds = fileIds;
        this.targetFolderId = targetFolderId;
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public long getTargetFolderId() {
        return targetFolderId;
    }

    public void setTargetFolderId(long targetFolderId) {
        this.targetFolderId = targetFolderId;
    }
}
