package com.fangcloud.core.sdk.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagingResult extends YfyBaseDTO {
    @JsonProperty("total_count")
    private long totalCount;
    @JsonProperty("page_id")
    private int pageId;
    @JsonProperty("page_capacity")
    private int pageCapacity;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageCapacity() {
        return pageCapacity;
    }

    public void setPageCapacity(int pageCapacity) {
        this.pageCapacity = pageCapacity;
    }
}
