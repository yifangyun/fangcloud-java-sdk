package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DownloadPreviewResult extends YfyBaseDTO {
    @JsonProperty("download_url")
    private String downloadUrl;
    private String status;
    @JsonProperty("representation_fail_reason")
    private String previewFailReason;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreviewFailReason() {
        return previewFailReason;
    }

    public void setPreviewFailReason(String previewFailReason) {
        this.previewFailReason = previewFailReason;
    }
}
