package com.fangcloud.sdk.api.admin.platform;

import com.fangcloud.sdk.api.admin.group.YfyDetailedGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class YfyMappingGroup extends YfyDetailedGroup {

    @JsonProperty("custom_id")
    private String customId;

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }
}
