package com.fangcloud.core.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyMiniItem {
    private long id;
    private String name;
    @JsonProperty("typed_id")
    private String typedId;
    @JsonProperty("user_id")
    private long userId;
    private long size;
    @JsonProperty("parent_folder_id")
    private long parentFolderId;
    @JsonProperty("in_trash")
    private boolean inTrash;
    @JsonProperty("is_deleted")
    private boolean isDeleted;

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

    public String getTypedId() {
        return typedId;
    }

    public void setTypedId(String typedId) {
        this.typedId = typedId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public boolean isInTrash() {
        return inTrash;
    }

    public void setInTrash(boolean inTrash) {
        this.inTrash = inTrash;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
