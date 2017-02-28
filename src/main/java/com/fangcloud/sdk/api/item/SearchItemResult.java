package com.fangcloud.sdk.api.item;

import com.fangcloud.sdk.api.PagingResult;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.folder.YfyFolder;

import java.util.List;

public class SearchItemResult extends PagingResult {
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
