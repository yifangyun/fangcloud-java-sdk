package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.api.PagingResult;
import com.fangcloud.sdk.api.file.YfyFile;

import java.util.List;

public class GetChildrenResult extends PagingResult {
    private List<YfyFile> files;
    private List<YfyFolder> folders;

    public List<YfyFile> getFiles() {
        return files;
    }

    public void setFiles(List<YfyFile> files) {
        this.files = files;
    }

    public List<YfyFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<YfyFolder> folders) {
        this.folders = folders;
    }
}
