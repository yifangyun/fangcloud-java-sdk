package com.fangcloud.sdk.api;

public enum IdentifierTypeEnum {
    EMAIL("email"),
    PHONE("phone"),
    USER_TICKET("user_ticket");

    private String type;

    IdentifierTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
