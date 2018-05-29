package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class GetPreviewTokenArg implements YfyArg {

    @JsonProperty("file_id")
    private Long fileId;
    @JsonProperty("file_version_id")
    private Long fileVersionId;
    private Integer period;

    public GetPreviewTokenArg(Long fileId, Integer period) {
        this.fileId = fileId;
        this.period = period;
    }

    public GetPreviewTokenArg(Long fileId, Long fileVersionId, Integer period) {
        this.fileId = fileId;
        this.fileVersionId = fileVersionId;
        this.period = period;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getFileVersionId() {
        return fileVersionId;
    }

    public void setFileVersionId(Long fileVersionId) {
        this.fileVersionId = fileVersionId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
