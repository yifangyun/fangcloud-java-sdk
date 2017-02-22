package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

public class ServerException extends YfyException {
    public ServerException(YfyErrorResponse errorResponse) {
        super(errorResponse.getRequestId(), errorResponse.getFirstError().getMsg());
    }
}
