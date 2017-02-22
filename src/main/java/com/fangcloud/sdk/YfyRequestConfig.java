package com.fangcloud.sdk;

import com.fangcloud.sdk.http.HttpRequestor;
import com.fangcloud.sdk.http.StandardHttpRequestor;

/**
 * A grouping of a few configuration parameters for how we should make requests to the
 * Fangcloud servers.
 */
public class YfyRequestConfig {
    private final HttpRequestor httpRequestor;

    public YfyRequestConfig() {
        this.httpRequestor = StandardHttpRequestor.INSTANCE;
    }

    public YfyRequestConfig(HttpRequestor httpRequestor) {
        if (httpRequestor == null) throw new NullPointerException("httpRequestor");

        this.httpRequestor = httpRequestor;
    }

    /**
     * The {@link HttpRequestor} implementation to use when making HTTP requests to the Fangcloud API
     * servers.
     *
     * <p> Defaults to {@link com.fangcloud.sdk.http.StandardHttpRequestor#INSTANCE}.
     *
     * @return HTTP requestor to use for issuing HTTP requests.
     */
    public HttpRequestor getHttpRequestor() {
        return httpRequestor;
    }

}
