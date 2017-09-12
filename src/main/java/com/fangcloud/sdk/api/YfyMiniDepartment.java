package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyMiniDepartment {
    private Long id;
    private String name;
    @JsonProperty("user_count")
    private Long userCount;
    @JsonProperty("children_departments_count")
    private Long childrenDepartmentsCount;
    @JsonProperty("direct_item_count")
    private Long directItemCount;

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

    public Long getChildrenDepartmentsCount() {
        return childrenDepartmentsCount;
    }

    public void setChildrenDepartmentsCount(Long childrenDepartmentsCount) {
        this.childrenDepartmentsCount = childrenDepartmentsCount;
    }

    public Long getDirectItemCount() {
        return directItemCount;
    }

    public void setDirectItemCount(Long directItemCount) {
        this.directItemCount = directItemCount;
    }
}
