package com.fangcloud.sdk.exception;

/**
 * The exception suggest the user need to authorise
 */
public class NeedAuthorizationException extends YfyException {
    public NeedAuthorizationException(String message) {
        super(message);
    }
}
