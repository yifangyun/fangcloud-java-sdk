package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MoveFolderArg implements YfyArg {
    @JsonProperty("folder_ids")
    private List<Long> folderIds;
    @JsonProperty("target_folder_id")
    private long targetFolderId;

    public MoveFolderArg(List<Long> folderIds, long targetFolderId) throws ClientValidationException {
        if (folderIds == null || folderIds.isEmpty()) {
            throw new ClientValidationException("folder ids can not be null or be empty");
        }
        this.folderIds = folderIds;
        this.targetFolderId = targetFolderId;
    }

    public List<Long> getFolderIds() {
        return folderIds;
    }

    public void setFolderIds(List<Long> folderIds) {
        this.folderIds = folderIds;
    }

    public long getTargetFolderId() {
        return targetFolderId;
    }

    public void setTargetFolderId(long targetFolderId) {
        this.targetFolderId = targetFolderId;
    }
}
