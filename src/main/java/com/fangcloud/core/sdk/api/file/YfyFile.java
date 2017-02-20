package com.fangcloud.core.sdk.api.file;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyFile {
    private long id;
    private String name;
    private long size;
    @JsonProperty("created_at")
    private long createdAt;

    public YfyFile() {}

    public YfyFile(long id, String name, long size, long createdAt) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
