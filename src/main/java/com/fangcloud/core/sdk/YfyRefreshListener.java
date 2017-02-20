package com.fangcloud.core.sdk;

public interface YfyRefreshListener {
    void tokenRefresh(String accessToken, String refreshToken, long expireIn);
}
