package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class YfyItem extends YfyBaseDTO {
    private Long id;
    private String type;
    private String name;
    private Long size;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("modified_at")
    private Long modifiedAt;
    private String description;
    private List<YfyPathFolder> path;
    @JsonProperty("owned_by")
    private YfyMiniUser ownedBy;
    private Boolean shared;
    private YfyPathFolder parent;
    private List<String> permissions;
    @JsonProperty("sequence_id")
    private Long sequenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
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

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
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

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }
}
