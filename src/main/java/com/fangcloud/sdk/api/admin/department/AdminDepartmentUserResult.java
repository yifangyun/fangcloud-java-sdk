package com.fangcloud.sdk.api.admin.department;

import com.fangcloud.sdk.api.PagingResult;
import com.fangcloud.sdk.api.user.YfyUser;

import java.util.List;

public class AdminDepartmentUserResult extends PagingResult {
    private List<YfyUser> users;

    public List<YfyUser> getUsers() {
        return users;
    }

    public void setUsers(List<YfyUser> users) {
        this.users = users;
    }
}
