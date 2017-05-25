package com.fangcloud.sdk;

/**
 * One must implements YfyRefreshListener if you use auto refreshing
 *
 * @param <T> User identify type in your account system
 */
public interface YfyRefreshListener<T> {

    /**
     * This method will be invoked when one's access token refreshed automatically
     *
     * @param key User identify who's access token is refreshed automatically
     * @param accessToken The User's new access token after refreshed(It will not be changed normally)
     * @param expireIn Expire time which access token is valid in this time
     */
    void onTokenRefreshed(T key, String accessToken, String refreshToken, long expireIn);
}
