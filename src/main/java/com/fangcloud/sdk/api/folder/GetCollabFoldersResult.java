package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.api.PagingResult;

import java.util.List;

public class GetCollabFoldersResult extends PagingResult {
    private List<YfyFolder> folders;

    public List<YfyFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<YfyFolder> folders) {
        this.folders = folders;
    }
}
