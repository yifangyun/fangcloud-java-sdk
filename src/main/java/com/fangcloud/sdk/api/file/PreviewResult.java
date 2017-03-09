package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviewResult extends YfyBaseDTO {
    private String category;
    @JsonProperty("download_url")
    private String downloadUrl;
    @JsonProperty("exif_rotation")
    private Integer exifRotation;
    private String format;
    @JsonProperty("has_2048")
    private Boolean has2048;
    @JsonProperty("page_count")
    private Long pageCount;
    private String status;
    @JsonProperty("representation_fail_reason")
    private String previewFailReason;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getExifRotation() {
        return exifRotation;
    }

    public void setExifRotation(Integer exifRotation) {
        this.exifRotation = exifRotation;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Boolean getHas2048() {
        return has2048;
    }

    public void setHas2048(Boolean has2048) {
        this.has2048 = has2048;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
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
