package com.fangcloud.sdk.api.user;

import com.fangcloud.sdk.YfyArg;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteUserArg implements YfyArg {
    @JsonProperty("user_receive_items")
    private Long userReceiveItems;

    public DeleteUserArg(Long userReceiveItems) {
        this.userReceiveItems = userReceiveItems;
    }

    public Long getUserReceiveItems() {
        return userReceiveItems;
    }

    public void setUserReceiveItems(Long userReceiveItems) {
        this.userReceiveItems = userReceiveItems;
    }
}
