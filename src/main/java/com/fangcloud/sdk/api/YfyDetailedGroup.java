package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyDetailedGroup {
    private Long id;
    private String name;
    private String description;
    private Long created;
    @JsonProperty("user_count")
    private Long userCount;
    @JsonProperty("admin_user")
    private YfyMiniUser adminUser;
    @JsonProperty("item_count")
    private Long itemCount;
    @JsonProperty("collab_auto_accepted")
    private Boolean collabAutoAccepted;
    @JsonProperty("is_visiable")
    private Boolean isVisiable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public YfyMiniUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(YfyMiniUser adminUser) {
        this.adminUser = adminUser;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

    public Boolean getCollabAutoAccepted() {
        return collabAutoAccepted;
    }

    public void setCollabAutoAccepted(Boolean collabAutoAccepted) {
        this.collabAutoAccepted = collabAutoAccepted;
    }

    public Boolean getVisiable() {
        return isVisiable;
    }

    public void setVisiable(Boolean visiable) {
        isVisiable = visiable;
    }
}
