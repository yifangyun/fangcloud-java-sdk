package com.fangcloud.core.sdk.api.file;

import com.fangcloud.core.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PreSignatureDownloadResult extends YfyBaseDTO {
    @JsonProperty("download_urls")
    private Map<Long, String> downloadUrls;

    public Map<Long, String> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(Map<Long, String> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }
}
