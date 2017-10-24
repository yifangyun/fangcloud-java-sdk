package com.fangcloud.sdk.api.group;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyGroup extends YfyBaseDTO {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("created_at")
    private Long created;
    @JsonProperty("user_count")
    private Long userCount;
    @JsonProperty("admin_user")
    private YfyMiniUser adminUser;

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
}
