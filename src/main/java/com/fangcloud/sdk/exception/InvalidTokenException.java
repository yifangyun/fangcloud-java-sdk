package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

public class InvalidTokenException extends YfyException {
    public InvalidTokenException(YfyErrorResponse errorResponse) {
        super(errorResponse);
    }
}
