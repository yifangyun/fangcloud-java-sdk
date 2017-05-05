package com.fangcloud.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * standard errors response in requesting to server
 */
public class YfyErrorResponse {
    private List<SpecificError> errors;
    @JsonProperty("request_id")
    private String requestId;

    public static class SpecificError {
        private String code;
        private String msg;
        private String field;

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

        @Override
        public String toString() {
            return code + " " + field + msg;
        }
    }

    public List<SpecificError> getErrors() {
        return errors;
    }

    public void setErrors(List<SpecificError> errors) {
        this.errors = errors;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
    this.requestId = requestId;
    }

    public SpecificError getFirstError() {
        return errors.get(0);
    }
}
