package com.fangcloud.core.sdk.exception;

import com.fangcloud.core.sdk.YfyErrorResponse;

public class NormalException extends YfyException {
    public NormalException(YfyErrorResponse errorResponse) {
        super(errorResponse.getRequestId(), errorResponse.getFirstError().getMsg());
    }
}
