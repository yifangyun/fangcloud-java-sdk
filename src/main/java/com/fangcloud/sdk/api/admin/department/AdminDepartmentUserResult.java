package com.fangcloud.sdk.api.admin.department;

import com.fangcloud.sdk.api.PagingResult;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fangcloud.sdk.api.user.YfyUser;

import java.util.List;

public class AdminDepartmentUserResult extends PagingResult {
    private List<YfyMiniUser> users;

    public List<YfyMiniUser> getUsers() {
        return users;
    }

    public void setUsers(List<YfyMiniUser> users) {
        this.users = users;
    }
}
