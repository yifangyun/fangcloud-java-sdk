package com.fangcloud.sdk.exception;

/**
 * Exception in json read mostly when response deserialization
 */
public class JsonReadException extends Exception {
    public static final long serialVersionUID = 0;

    public String jsonStr;

    public JsonReadException(Throwable cause) {
        super(cause);
    }

    public JsonReadException(String jsonStr, Throwable cause) {
        super(cause);
        this.jsonStr = jsonStr;
    }

    public String getJsonStr() {
        return jsonStr;
    }
}
