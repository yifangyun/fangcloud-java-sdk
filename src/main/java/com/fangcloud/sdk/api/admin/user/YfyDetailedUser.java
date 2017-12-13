package com.fangcloud.sdk.api.admin.user;

import com.fangcloud.sdk.api.user.YfyUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YfyDetailedUser extends YfyUser {
    @JsonProperty("hide_phone")
    private Boolean hidePhone;
    @JsonProperty("disable_download")
    private Boolean disableDownload;
    @JsonProperty("space_used")
    private Long spaceUsed;
    @JsonProperty("space_total")
    private Long spaceTotal;

    public Boolean getHidePhone() {
        return hidePhone;
    }

    public void setHidePhone(Boolean hidePhone) {
        this.hidePhone = hidePhone;
    }

    public Boolean getDisableDownload() {
        return disableDownload;
    }

    public void setDisableDownload(Boolean disableDownload) {
        this.disableDownload = disableDownload;
    }

    public Long getSpaceUsed() {
        return spaceUsed;
    }

    public void setSpaceUsed(Long spaceUsed) {
        this.spaceUsed = spaceUsed;
    }

    public Long getSpaceTotal() {
        return spaceTotal;
    }

    public void setSpaceTotal(Long spaceTotal) {
        this.spaceTotal = spaceTotal;
    }
}
