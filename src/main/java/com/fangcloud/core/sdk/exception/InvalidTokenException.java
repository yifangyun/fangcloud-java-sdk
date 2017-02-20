package com.fangcloud.core.sdk.exception;

import com.fangcloud.core.sdk.YfyErrorResponse;

public class InvalidTokenException extends NormalException {
    public InvalidTokenException(YfyErrorResponse errorResponse) {
        super(errorResponse);
    }
}
