package com.fangcloud.sdk.api;

public enum AccessibleByTypeEnum {
    USER("user"),
    GROUP("group"),
    DEPARTMENT("department");

    private String type;

    AccessibleByTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
