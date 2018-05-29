package com.fangcloud.sdk.api.admin.platform;

import com.fangcloud.sdk.api.admin.department.YfyDetailedDepartment;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class YfyMappingDepartment extends YfyDetailedDepartment {

    @JsonProperty("custom_id")
    private String customId;

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }
}
