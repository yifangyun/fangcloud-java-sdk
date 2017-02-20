package com.fangcloud.core.sdk;

import com.fangcloud.core.sdk.api.file.YfyFileRequest;
import com.fangcloud.core.sdk.api.user.YfyUserRequest;
import com.fangcloud.core.sdk.auth.YfyAuthFinish;
import com.fangcloud.core.sdk.exception.InvalidTokenException;
import com.fangcloud.core.sdk.exception.NeedAuthorizationException;
import com.fangcloud.core.sdk.exception.YfyException;
import com.fangcloud.core.sdk.http.HttpRequestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class YfyClient {
    private static final String USER_AGENT_ID = "OfficialFangcloudJavaSDK";

    private volatile String accessToken;
    private volatile String refreshToken;
    private boolean autoRefresh;
    private volatile long lastRefresh;
    private final ReadWriteLock refreshLock;
    private final YfyRequestConfig requestConfig;
    private final YfyHost host;
    private final YfyFileRequest fileRequest;
    private final YfyUserRequest userRequest;
    private YfyRefreshListener refreshListener;

    public YfyClient(YfyRequestConfig requestConfig,
                     String accessToken,
                     String refreshToken,
                     YfyRefreshListener refreshListener) {
        if (accessToken == null) {
            throw new NullPointerException("access token");
        }
        this.requestConfig = requestConfig;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.host = YfyAppInfo.getHost();
        this.fileRequest = new YfyFileRequest(this);
        this.userRequest = new YfyUserRequest(this);
        if (refreshToken != null && refreshListener != null) {
            this.autoRefresh = true;
            this.refreshListener = refreshListener;
        }
        this.refreshLock = new ReentrantReadWriteLock();
    }


    public YfyClient(YfyRequestConfig requestConfig, String accessToken, String refreshToken) {
        this(requestConfig, accessToken, refreshToken, null);
    }

    public YfyClient(YfyRequestConfig requestConfig, String accessToken) {
        this(requestConfig, accessToken, null);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        if (autoRefresh) {
            if (refreshToken == null) {
                throw new NullPointerException("refresh token");
            }
            if (refreshListener == null) {
                throw new NullPointerException("refresh listener");
            }
        }
        this.autoRefresh = autoRefresh;
    }

    public long getLastRefresh() {
        return lastRefresh;
    }

    public YfyRequestConfig getRequestConfig() {
        return requestConfig;
    }

    public YfyHost getHost() {
        return host;
    }

    public YfyFileRequest files() {
        return fileRequest;
    }

    public YfyUserRequest users() {
        return userRequest;
    }

    public boolean canRefresh() {
        return autoRefresh && refreshToken != null;
    }

    public <T> T doGet(final String host,
                       final String path,
                       final Object[] listParams,
                       final Map<String, String> mapParams,
                       final Class<T> tClass)
            throws YfyException {
        return executeRetriable(new RetriableExecution<T>() {
            @Override
            public T execute(boolean isRefresh) throws YfyException {
                final List<HttpRequestor.Header> headers = addApiHeaders(isRefresh);
                return YfyRequestUtil.doGetNoAuth(
                        requestConfig, host, String.format(path, listParams), mapParams, headers, tClass);
            }
        });
    }

    public <T> T doPost(final String host,
                        final String path,
                        final Object[] listParams,
                        final YfyArg arg,
                        final String method,
                        final Class<T> tClass)
            throws YfyException {
        return executeRetriable(new RetriableExecution<T>() {
            @Override
            public T execute(boolean isRefresh) throws YfyException {
                final List<HttpRequestor.Header> headers = addApiHeaders(isRefresh);
                return YfyRequestUtil.doPostNoAuth(
                        requestConfig, host, String.format(path, listParams), arg, method, headers, tClass);
            }
        });
    }

    private List<HttpRequestor.Header> addApiHeaders(boolean isRefresh) throws NeedAuthorizationException {
        final List<HttpRequestor.Header> headers = new ArrayList<HttpRequestor.Header>();
        YfyRequestUtil.addAuthHeader(headers, lockAccessToken(isRefresh));
        YfyRequestUtil.addApiCustomHeader(headers, USER_AGENT_ID);
        return headers;
    }

    private String lockAccessToken(boolean isRefresh) throws NeedAuthorizationException {
        if (isRefresh) {
            refreshLock.writeLock().lock();
            try {
                if (!isRefreshJustNow()) {
                    refresh();
                }
            } finally {
                refreshLock.writeLock().unlock();
            }
        } else {
            refreshLock.readLock().lock();
            refreshLock.readLock().unlock();
        }
        return accessToken;
    }

    /**
     * refresh user token,can be used by developer, must be lock.
     */
    public void refresh() throws NeedAuthorizationException {
        if (refreshToken == null) {
            throw new IllegalStateException("refresh token is null,can't refresh");
        }
        refreshLock.writeLock().lock();
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);

        try {
            YfyAuthFinish finish = YfyRequestUtil.doPostInAuth(
                    requestConfig, host.getAuth(), "oauth/token", params, YfyAuthFinish.class);
            accessToken = finish.getAccessToken();
            refreshToken = finish.getRefreshToken();
            lastRefresh = System.currentTimeMillis();
            refreshListener.tokenRefresh(accessToken, refreshToken, finish.getExpiresIn());
        } catch (YfyException ex) {
            if (ex instanceof InvalidTokenException) {
                throw new NeedAuthorizationException(ex.getRequestId(), ex.getMessage());
            }
        } finally {
            refreshLock.writeLock().unlock();
        }
    }

    private boolean isRefreshJustNow() {
        return (System.currentTimeMillis() - lastRefresh < 600000);
    }

    /**
     * Retries the execution at most a maximum number of times. Mostly used when access token is expired.
     */
    private <T> T executeRetriable(RetriableExecution<T> execution) throws YfyException {
        int maxRetries = 1;
        int retries = 0;
        boolean isRefresh = false;
        while (true) {
            try {
                return execution.execute(isRefresh);
            } catch (YfyException ex) {
                if (retries < maxRetries && ex instanceof InvalidTokenException && canRefresh()) {
                    ++retries;
                    isRefresh = true;
                } else {
                    throw ex;
                }
            }
        }
    }

    private interface RetriableExecution<T> {
        T execute(boolean isFresh) throws YfyException;
    }
}
