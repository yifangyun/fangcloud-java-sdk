package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

import java.util.List;
import java.util.Map;

public class RateLimitException extends YfyException {
    private int rateLimitReset;

    public RateLimitException(YfyErrorResponse errorResponse, Map<String, List<String>> headers) {
        super(errorResponse);
        List<String> value = headers.get("X-Rate-Limit-Reset");
        if (value != null && !value.isEmpty()) {
            rateLimitReset = Integer.parseInt(value.get(0));
        }
    }

    public int getRateLimitReset() {
        return rateLimitReset;
    }
}
