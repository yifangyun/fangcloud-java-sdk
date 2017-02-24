package com.fangcloud.sdk;

public interface YfyRefreshListener<T> {
    void tokenRefresh(T key, String accessToken, String refreshToken, long expireIn);
}
