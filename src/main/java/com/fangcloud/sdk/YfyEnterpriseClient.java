package com.fangcloud.sdk;

import com.fangcloud.sdk.api.admin.department.YfyAdminDepartmentRequest;
import com.fangcloud.sdk.api.admin.group.YfyAdminGroupRequest;
import com.fangcloud.sdk.api.admin.user.YfyAdminUserRequest;

public class YfyEnterpriseClient<K> extends YfyBaseClient<K> {
    private final YfyAdminDepartmentRequest adminDepartmentRequest;
    private final YfyAdminGroupRequest adminGroupRequest;
    private final YfyAdminUserRequest adminUserRequest;

    public YfyEnterpriseClient(K key,
                               YfyRequestConfig requestConfig,
                               String accessToken,
                               String refreshToken,
                               YfyRefreshListener<K> refreshListener) {
        super(key, requestConfig, accessToken, refreshToken, refreshListener);
        YfyInternalClient internalClient = new YfyInternalClient();
        this.adminDepartmentRequest = new YfyAdminDepartmentRequest(internalClient);
        this.adminGroupRequest = new YfyAdminGroupRequest(internalClient);
        this.adminUserRequest = new YfyAdminUserRequest(internalClient);
    }

    public YfyEnterpriseClient(K key, YfyRequestConfig requestConfig, String accessToken, String refreshToken) {
        this(key, requestConfig, accessToken, refreshToken, null);
    }

    public YfyEnterpriseClient(YfyRequestConfig requestConfig, String accessToken) {
        this(null, requestConfig, accessToken, null);
    }

    public YfyAdminDepartmentRequest adminDepartments() {
        return adminDepartmentRequest;
    }

    public YfyAdminGroupRequest adminGroups() {
        return adminGroupRequest;
    }

    public YfyAdminUserRequest adminUsers() {
        return adminUserRequest;
    }

}
