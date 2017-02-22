package com.fangcloud.core.sdk.api.item;

import com.fangcloud.core.sdk.api.YfyBaseDTO;
import com.fangcloud.core.sdk.api.YfyMiniUser;
import com.fangcloud.core.sdk.api.YfyPathFolder;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class YfyItem extends YfyBaseDTO {
    private long id;
    private String type;
    private String name;
    private long size;
    @JsonProperty("created_at")
    private long createdAt;
    @JsonProperty("modified_at")
    private long modifiedAt;
    private String description;
    private List<YfyPathFolder> path;
    @JsonProperty("owned_by")
    private YfyMiniUser ownedBy;
    private boolean shared;
    private YfyPathFolder parent;
    private List<String> permissions;
    @JsonProperty("sequence_id")
    private long sequenceId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<YfyPathFolder> getPath() {
        return path;
    }

    public void setPath(List<YfyPathFolder> path) {
        this.path = path;
    }

    public YfyMiniUser getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(YfyMiniUser ownedBy) {
        this.ownedBy = ownedBy;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public YfyPathFolder getParent() {
        return parent;
    }

    public void setParent(YfyPathFolder parent) {
        this.parent = parent;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
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
