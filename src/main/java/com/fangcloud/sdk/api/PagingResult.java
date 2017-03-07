package com.fangcloud.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagingResult extends YfyBaseDTO {
    @JsonProperty("total_count")
    private Long totalCount;
    @JsonProperty("page_id")
    private Integer pageId;
    @JsonProperty("page_capacity")
    private Integer pageCapacity;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPageCapacity() {
        return pageCapacity;
    }

    public void setPageCapacity(Integer pageCapacity) {
        this.pageCapacity = pageCapacity;
    }
}
