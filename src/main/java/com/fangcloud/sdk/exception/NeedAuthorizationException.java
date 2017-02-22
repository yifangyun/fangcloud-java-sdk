package com.fangcloud.sdk.exception;

public class NeedAuthorizationException extends YfyException {
    public NeedAuthorizationException(String requestId, String message) {
        super(requestId, message);
    }
}
