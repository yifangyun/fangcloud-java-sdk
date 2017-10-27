package com.fangcloud.sdk.api;

/**
 * Type of the query filter: "file_name", "content" and "all"
 */
public enum QueryFilterEnum {
    FILE_NAME("file_name"),
    CONTENT("content"),
    ALL("all");

    private String type;

    QueryFilterEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
