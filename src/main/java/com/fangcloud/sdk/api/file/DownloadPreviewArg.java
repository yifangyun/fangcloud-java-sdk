package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DownloadPreviewArg implements YfyArg {
    @JsonProperty("page_index")
    private int pageIndex;
    private String kind;

    public DownloadPreviewArg(int pageIndex, String kind) {
        this.pageIndex = pageIndex;
        this.kind = kind;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
