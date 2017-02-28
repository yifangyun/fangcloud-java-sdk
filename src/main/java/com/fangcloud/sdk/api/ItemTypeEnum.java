package com.fangcloud.sdk.api;

/**
 * Type of the item: "file", "folder" and "item"
 */
public enum ItemTypeEnum {
    FILE("file"),
    FOLDER("folder"),
    ITEM("item");

    private String type;

    ItemTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
