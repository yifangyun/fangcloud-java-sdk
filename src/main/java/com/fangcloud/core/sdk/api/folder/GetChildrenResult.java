package com.fangcloud.core.sdk.api.folder;

import com.fangcloud.core.sdk.api.PagingResult;
import com.fangcloud.core.sdk.api.YfyFileInfo;
import com.fangcloud.core.sdk.api.YfyFolderInfo;

import java.util.List;

public class GetChildrenResult extends PagingResult {
    private List<YfyFileInfo> files;
    private List<YfyFolderInfo> folders;

    public List<YfyFileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<YfyFileInfo> files) {
        this.files = files;
    }

    public List<YfyFolderInfo> getFolders() {
        return folders;
    }

    public void setFolders(List<YfyFolderInfo> folders) {
        this.folders = folders;
    }
}
