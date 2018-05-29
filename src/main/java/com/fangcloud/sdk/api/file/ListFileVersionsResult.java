package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyFileVersion;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class ListFileVersionsResult extends YfyBaseDTO {

    @JsonProperty("file_versions")
    private List<YfyFileVersion> fileVersions;

    public List<YfyFileVersion> getFileVersions() {
        return fileVersions;
    }

    public void setFileVersions(List<YfyFileVersion> fileVersions) {
        this.fileVersions = fileVersions;
    }
}
