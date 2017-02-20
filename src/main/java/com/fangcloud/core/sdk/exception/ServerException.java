package com.fangcloud.core.sdk.exception;

import com.fangcloud.core.sdk.YfyErrorResponse;

public class ServerException extends YfyException {
    public ServerException(YfyErrorResponse errorResponse) {
        super(errorResponse.getRequestId(), errorResponse.getFirstError().getMsg());
    }
}
