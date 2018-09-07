package com.fangcloud.sdk.api.admin.user;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserLoginParamsResult extends YfyBaseDTO {
    @JsonProperty("auth_key")
    private String authKey;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("expires_in")
    private String expiresIn;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public GetUserLoginParamsResult(String authKey, String clientId, String expiresIn) {
        this.authKey = authKey;
        this.clientId = clientId;
        this.expiresIn = expiresIn;
    }

    public GetUserLoginParamsResult() {
    }
}
