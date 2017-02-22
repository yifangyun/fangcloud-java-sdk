package com.fangcloud.core.sdk.api.file;

import com.fangcloud.core.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PreSignatureUploadArg implements YfyArg {
    @JsonProperty("parent_id")
    private long parentId;
    private String name;
    @JsonProperty("upload_type")
    private String uploadType;


    public PreSignatureUploadArg() {
    }

    public PreSignatureUploadArg(long parentId, String name, String uploadType) {
        this.parentId = parentId;
        this.name = name;
        this.uploadType = uploadType;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }
}
