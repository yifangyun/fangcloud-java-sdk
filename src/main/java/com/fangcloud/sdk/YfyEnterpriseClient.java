package com.fangcloud.sdk;

import com.fangcloud.sdk.api.admin.department.YfyAdminDepartmentRequest;
import com.fangcloud.sdk.api.admin.group.YfyAdminGroupRequest;

public class YfyEnterpriseClient<K> extends YfyBaseClient<K> {
    private final YfyAdminDepartmentRequest adminDepartmentRequest;
    private final YfyAdminGroupRequest adminGroupRequest;

    public YfyEnterpriseClient(K key,
                               YfyRequestConfig requestConfig,
                               String accessToken,
                               String refreshToken,
                               YfyRefreshListener<K> refreshListener) {
        super(key, requestConfig, accessToken, refreshToken, refreshListener);
        YfyInternalClient internalClient = new YfyInternalClient();
        this.adminDepartmentRequest = new YfyAdminDepartmentRequest(internalClient);
        this.adminGroupRequest = new YfyAdminGroupRequest(internalClient);
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

}
