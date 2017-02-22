package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

public class InvalidTokenException extends NormalException {
    public InvalidTokenException(YfyErrorResponse errorResponse) {
        super(errorResponse);
    }
}
