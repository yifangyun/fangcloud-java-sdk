package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class YfyFileVersion extends YfyBaseDTO {

    private Long id;
    @JsonProperty("file_id")
    private Long fileId;
    private String name;
    private Long size;
    private String sha1;
    @JsonProperty("modified_at")
    private Long modifiedAt;
    private String description;
    private String remark;
    private Boolean current;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}
