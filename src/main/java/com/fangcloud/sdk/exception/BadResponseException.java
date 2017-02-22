package com.fangcloud.sdk.exception;

/**
 * Thrown when we the response from the Fangcloud server isn't something we expect.
 * For example, if the JSON returned by the server is malformed or missing fields.
 */
public class BadResponseException extends YfyException {
    private static final long serialVersionUID = 0;

    public BadResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}

