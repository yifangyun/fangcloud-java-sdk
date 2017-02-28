package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteFileFromTrashArg implements YfyArg {
    @JsonProperty("file_ids")
    private List<Long> fileIds;
    @JsonProperty("clear_trash")
    private boolean clearTrash;

    public DeleteFileFromTrashArg(List<Long> fileIds, boolean clearTrash) {
        this.fileIds = fileIds;
        this.clearTrash = clearTrash;
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public boolean isClearTrash() {
        return clearTrash;
    }

    public void setClearTrash(boolean clearTrash) {
        this.clearTrash = clearTrash;
    }
}
