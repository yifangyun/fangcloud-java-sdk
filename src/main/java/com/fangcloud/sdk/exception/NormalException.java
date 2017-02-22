package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

public class NormalException extends YfyException {
    public NormalException(YfyErrorResponse errorResponse) {
        super(errorResponse.getRequestId(), errorResponse.getFirstError().getMsg());
    }
}
