package com.fangcloud.sdk.api.user;

import com.fangcloud.sdk.api.PagingResult;
import com.fangcloud.sdk.api.YfyMiniUser;

import java.util.List;

public class SearchUserResult extends PagingResult {
    private List<YfyMiniUser> users;

    public List<YfyMiniUser> getUsers() {
        return users;
    }

    public void setUsers(List<YfyMiniUser> users) {
        this.users = users;
    }
}
