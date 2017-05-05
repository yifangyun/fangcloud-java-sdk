package com.fangcloud.sdk.api;

public enum ShareLinkAccessEnum {
    PUBLIC("public"),
    COMPANY("company");

    private String access;

    ShareLinkAccessEnum(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }
}
