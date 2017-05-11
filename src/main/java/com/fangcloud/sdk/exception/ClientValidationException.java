package com.fangcloud.sdk.exception;

/**
 * Some client validation to avoid wrong parameter
 */
public class ClientValidationException extends YfyException {
    public ClientValidationException(String message) {
        super(message);
    }
}
