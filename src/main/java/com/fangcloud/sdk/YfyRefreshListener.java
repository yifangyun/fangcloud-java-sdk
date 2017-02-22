package com.fangcloud.sdk;

public interface YfyRefreshListener {
    void tokenRefresh(String accessToken, String refreshToken, long expireIn);
}
