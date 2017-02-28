package com.fangcloud.sdk;

import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.file.YfyFileRequest;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.api.user.YfyUserRequest;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.exception.BadResponseException;
import com.fangcloud.sdk.exception.InvalidTokenException;
import com.fangcloud.sdk.exception.JsonReadException;
import com.fangcloud.sdk.exception.NeedAuthorizationException;
import com.fangcloud.sdk.exception.NetworkIOException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.http.HttpRequestor;
import com.fangcloud.sdk.util.IOUtil;
import com.fangcloud.sdk.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class YfyClient<K> {
    private final YfyFileRequest fileRequest;
    private final YfyUserRequest userRequest;
    private final YfyFolderRequest folderRequest;

    private K key;
    private volatile String accessToken;
    private volatile String refreshToken;
    private boolean autoRefresh;
    private YfyRefreshListener<K> refreshListener;
    private volatile long lastRefresh;
    private final ReadWriteLock refreshLock;
    private final YfyRequestConfig requestConfig;
    private final YfyHost host;

    public YfyClient(K key,
                     YfyRequestConfig requestConfig,
                     String accessToken,
                     String refreshToken,
                     YfyRefreshListener<K> refreshListener) {
        YfyInternalClient internalClient = new YfyInternalClient();
        if (accessToken == null) {
            throw new NullPointerException("access token");
        }
        this.fileRequest = new YfyFileRequest(internalClient);
        this.userRequest = new YfyUserRequest(internalClient);
        this.folderRequest = new YfyFolderRequest(internalClient);

        this.requestConfig = requestConfig;
        this.host = YfyAppInfo.getHost();
        this.refreshLock = new ReentrantReadWriteLock();

        this.key = key;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        if (refreshToken != null && refreshListener != null) {
            this.autoRefresh = true;
            this.refreshListener = refreshListener;
        }
    }

    public YfyClient(K key, YfyRequestConfig requestConfig, String accessToken, String refreshToken) {
        this(key, requestConfig, accessToken, refreshToken, null);
    }

    public YfyClient(K key, YfyRequestConfig requestConfig, String accessToken) {
        this(key, requestConfig, accessToken, null);
    }

    public YfyFileRequest files() {
        return fileRequest;
    }

    public YfyUserRequest users() {
        return userRequest;
    }

    public YfyFolderRequest folders() {
        return folderRequest;
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
            refreshListener.tokenRefresh(key, accessToken, refreshToken, finish.getExpiresIn());
        } catch (YfyException ex) {
            if (ex instanceof InvalidTokenException) {
                throw new NeedAuthorizationException("The user need authorization again");
            }
        } finally {
            refreshLock.writeLock().unlock();
        }
    }


    public class YfyInternalClient {
        private static final String USER_AGENT_ID = "OfficialFangcloudJavaSDK";
        private static final String BOUNDARY = "--WebKitFormBoundaryPjbcBBB6fBCxfBFq";
        private static final String BOUNDARY_STR = "--" + BOUNDARY;

        public YfyHost getHost() {
            return host;
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

        public InputStream doDownload(String downloadUrl) throws YfyException {
            try {
                HttpRequestor.Response response =
                        requestConfig.getHttpRequestor().doGet(downloadUrl, new ArrayList<HttpRequestor.Header>());
                if (response.getStatusCode() != 200) {
                    throw YfyRequestUtil.unexpectedStatus(response);
                } else {
                    return response.getBody();
                }

            } catch (IOException ex) {
                throw new NetworkIOException(ex);
            } catch (JsonReadException ex) {
                throw new BadResponseException("Bad JSON in response : " + ex.getMessage(), ex);
            }
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

        public YfyFile doUpload(String uploadUrl, String filePath) throws YfyException {
            List<HttpRequestor.Header> headers = new ArrayList<HttpRequestor.Header>();
            headers.add(new HttpRequestor.Header("Content-Type", "multipart/form-data; boundary=" + BOUNDARY));

            try {
                HttpRequestor.Uploader uploader = requestConfig.getHttpRequestor().startPost(
                        YfySdkConstant.POST_METHOD, uploadUrl, headers);
                try {
                    writeMultipartData(uploader.getBody(), filePath);
                    return YfyRequestUtil.finishResponse(uploader.finish(), YfyFile.class);
                } finally {
                    uploader.close();
                }
            } catch (IOException ex) {
                throw new NetworkIOException(ex);
            }

        }

        private void writeMultipartData(OutputStream outputStream, String filePath) throws IOException {
            String[] filePathSplit = filePath.split(File.separator);
            int fileNameIndex = filePathSplit.length - 1;
            String fileName = filePathSplit[fileNameIndex];
            StringBuilder sb = new StringBuilder(BOUNDARY_STR);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
            sb.append("Content-Type: application/octet-stream");
            sb.append("\r\n\r\n");
            outputStream.write(StringUtil.stringToUtf8(sb.toString()));
            try {
                IOUtil.copyStreamToStream(new FileInputStream(filePath), outputStream);
                outputStream.write(StringUtil.stringToUtf8("\r\n\r\n" + BOUNDARY_STR + "--"));
            } catch (IOUtil.ReadException ex) {
                throw ex.getCause();
            } finally {
                outputStream.close();
            }
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
                    if (ex instanceof InvalidTokenException && canRefresh()) {
                        if (retries < maxRetries) {
                            ++retries;
                            isRefresh = true;
                        } else {
                            throw new NeedAuthorizationException("The user need authorization again");
                        }
                    } else {
                        throw ex;
                    }
                }
            }
        }
    }

    private interface RetriableExecution<T> {
        T execute(boolean isFresh) throws YfyException;
    }
}
