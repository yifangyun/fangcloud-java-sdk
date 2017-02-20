package com.fangcloud.core.sdk.auth;

import com.fangcloud.core.sdk.YfyClient;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * When you successfully complete the authorization process, the Fangcloud server returns
 * this information to you.
 */
public final class YfyAuthFinish {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    private String scope;

    public YfyAuthFinish() {}

    /**
     * @param accessToken OAuth access token
     * @param refreshToken OAuth refresh token
     * @param expiresIn access token's rest expire time
     */
    public YfyAuthFinish(String accessToken, String refreshToken, long expiresIn, String tokenType, String scope) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.scope = scope;
    }

    /**
     * Returns an <em>access token</em> that can be used to make Fangcloud API calls.  Pass this in to
     * the {@link YfyClient} constructor.
     */
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns <em>refresh token</em> that can be used to refresh <em>access token</em> and itself.Usually
     * use when access token is expired. Pass this in to the {@link YfyClient} constructor.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Returns the expire time which access token is valid in this time
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * token type, must be "bearer" now
     */
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * authorization scope, only "all" now
     */
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
