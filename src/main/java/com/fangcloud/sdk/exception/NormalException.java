package com.fangcloud.sdk.exception;

import com.fangcloud.sdk.YfyErrorResponse;

/**
 * Normal exception usually cased by wrong resource, like wrong permission
 */
public class NormalException extends YfyException {
    private String code;
    private String msg;
    private String field;

    public NormalException(YfyErrorResponse errorResponse) {
        super(errorResponse);
        YfyErrorResponse.SpecificError error = errorResponse.getFirstError();
        this.code = error.getCode();
        this.msg = error.getMsg();
        this.field = error.getField();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
