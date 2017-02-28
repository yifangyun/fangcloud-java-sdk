package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviewResult extends YfyBaseDTO {
    private String category;
    @JsonProperty("download_url")
    private String download_Url;
    @JsonProperty("exif_rotation")
    private int exifRotation;
    private String format;
    @JsonProperty("has_2048")
    private boolean has2048;
    @JsonProperty("page_count")
    private long pageCount;
    private String status;
    @JsonProperty("representation_fail_reason")
    private String previewFailReason;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDownload_Url() {
        return download_Url;
    }

    public void setDownload_Url(String download_Url) {
        this.download_Url = download_Url;
    }

    public int getExifRotation() {
        return exifRotation;
    }

    public void setExifRotation(int exifRotation) {
        this.exifRotation = exifRotation;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isHas2048() {
        return has2048;
    }

    public void setHas2048(boolean has2048) {
        this.has2048 = has2048;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
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
