package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

/**
 * Server side exception usually caused by server bug, report to us
 */
public class ServerException extends YfyException {
    public ServerException(YfyErrorResponse errorResponse) {
        super(errorResponse);
    }
}
