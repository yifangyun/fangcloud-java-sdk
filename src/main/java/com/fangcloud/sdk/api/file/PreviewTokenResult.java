package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author just-cj
 * @date 2018/5/29
 */
public class PreviewTokenResult extends YfyBaseDTO {

    @JsonProperty("preview_token")
    private String previewToken;

    public String getPreviewToken() {
        return previewToken;
    }

    public void setPreviewToken(String previewToken) {
        this.previewToken = previewToken;
    }
}
