package com.fangcloud.sdk.exception;

/**
 * Other error exception, return by other server, like upload and download server
 */
public class OtherErrorException extends YfyException {
    public OtherErrorException(String msg) {
        super(msg);
    }
}
