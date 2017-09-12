package com.fangcloud.sdk.api.department;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyDepartment extends YfyBaseDTO {
    private Long id;
    private String name;
    @JsonProperty("user_count")
    private Long userCount;
    @JsonProperty("parent_id")
    private Long parentId;

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

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
