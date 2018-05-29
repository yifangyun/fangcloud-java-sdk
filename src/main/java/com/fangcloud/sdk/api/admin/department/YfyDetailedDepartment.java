package com.fangcloud.sdk.api.admin.department;

import com.fangcloud.sdk.api.YfyMiniUser;
import com.fangcloud.sdk.api.department.YfyDepartment;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class YfyDetailedDepartment extends YfyDepartment {

    private YfyMiniUser director;
    private long spaceUsed;
    private long spaceTotal;
    @JsonProperty("special_users")
    private List<YfyMiniUser> specialUsers;
    @JsonProperty("hide_phone")
    private Boolean hidePhone;
    @JsonProperty("disable_share")
    private Boolean disableShare;
    @JsonProperty("enable_watermark")
    private Boolean enableWatermark;
    @JsonProperty("collab_auto_accepted")
    private Boolean collabAutoAccepted;

    public YfyMiniUser getDirector() {
        return director;
    }

    public void setDirector(YfyMiniUser director) {
        this.director = director;
    }

    public long getSpaceUsed() {
        return spaceUsed;
    }

    public void setSpaceUsed(long spaceUsed) {
        this.spaceUsed = spaceUsed;
    }

    public long getSpaceTotal() {
        return spaceTotal;
    }

    public void setSpaceTotal(long spaceTotal) {
        this.spaceTotal = spaceTotal;
    }

    public List<YfyMiniUser> getSpecialUsers() {
        return specialUsers;
    }

    public void setSpecialUsers(List<YfyMiniUser> specialUsers) {
        this.specialUsers = specialUsers;
    }

    public Boolean getHidePhone() {
        return hidePhone;
    }

    public void setHidePhone(Boolean hidePhone) {
        this.hidePhone = hidePhone;
    }

    public Boolean getDisableShare() {
        return disableShare;
    }

    public void setDisableShare(Boolean disableShare) {
        this.disableShare = disableShare;
    }

    public Boolean getEnableWatermark() {
        return enableWatermark;
    }

    public void setEnableWatermark(Boolean enableWatermark) {
        this.enableWatermark = enableWatermark;
    }

    public Boolean getCollabAutoAccepted() {
        return collabAutoAccepted;
    }

    public void setCollabAutoAccepted(Boolean collabAutoAccepted) {
        this.collabAutoAccepted = collabAutoAccepted;
    }
}
