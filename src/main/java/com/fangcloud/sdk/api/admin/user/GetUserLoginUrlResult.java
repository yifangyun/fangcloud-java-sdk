package com.fangcloud.sdk.api.admin.user;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserLoginUrlResult extends YfyBaseDTO {
    @JsonProperty("login_url")
    private String loginUrl;
    @JsonProperty("expires_in")
    private String expiresIn;


    public GetUserLoginUrlResult(String loginUrl, String expiresIn) {
        this.loginUrl = loginUrl;
        this.expiresIn = expiresIn;
    }

    public GetUserLoginUrlResult() {
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
