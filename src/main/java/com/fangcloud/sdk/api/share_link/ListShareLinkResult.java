package com.fangcloud.sdk.api.share_link;

import com.fangcloud.sdk.api.PagingResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListShareLinkResult extends PagingResult {
    @JsonProperty("share_links")
    private List<YfyShareLink> shareLinks;

    public List<YfyShareLink> getShareLinks() {
        return shareLinks;
    }

    public void setShareLinks(List<YfyShareLink> shareLinks) {
        this.shareLinks = shareLinks;
    }
}
