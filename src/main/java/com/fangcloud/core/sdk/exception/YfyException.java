package com.fangcloud.core.sdk.exception;

/**
 * The base exception thrown by Fangcloud API calls.  Normally, you'll need to do something specific
 * for {@link InvalidTokenException} and possibly for refresh and retry.  The rest you
 * should probably just log and investigate manually.
 */
public class YfyException extends Exception {
    private static final long serialVersionUID = 0L;

    private final String requestId;

    public YfyException(String message) {
        this(null, message);
    }

    public YfyException(String requestId, String message) {
        super(message);
        this.requestId = requestId;
    }

    public YfyException(String message, Throwable cause) {
        this(null, message, cause);
    }

    public YfyException(String requestId, String message, Throwable cause) {
        super(message, cause);
        this.requestId = requestId;
    }

    /**
     * Return the unique ID associated with the request that triggered this exception.
     *
     * <p> The ID may be {@code null} if we could not receive a response from the Fangcloud servers
     * (e.g. from a {@link NetworkIOException}).
     *
     * <p> Please use this ID when filing bug reports.
     *
     * @return unique ID associated with the request that caused this exception, or {@code null} if
     * one is not available.
     */
    public String getRequestId() {
        return requestId;
    }
}
