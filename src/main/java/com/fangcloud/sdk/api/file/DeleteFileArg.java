package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteFileArg implements YfyArg {
    @JsonProperty("file_ids")
    private List<Long> fileIds;

    public DeleteFileArg(List<Long> fileIds) throws ClientValidationException {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new ClientValidationException("file ids can not be null or be empty");
        }
        this.fileIds = fileIds;
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }
}
