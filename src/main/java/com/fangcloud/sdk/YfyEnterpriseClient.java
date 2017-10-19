package com.fangcloud.sdk;

import com.fangcloud.sdk.api.admin.department.YfyAdminDepartmentRequest;

public class YfyEnterpriseClient<K> extends YfyBaseClient<K> {
    private final YfyAdminDepartmentRequest adminDepartmentRequest;

    public YfyEnterpriseClient(K key,
                               YfyRequestConfig requestConfig,
                               String accessToken,
                               String refreshToken,
                               YfyRefreshListener<K> refreshListener) {
        super(key, requestConfig, accessToken, refreshToken, refreshListener);
        YfyInternalClient internalClient = new YfyInternalClient();
        this.adminDepartmentRequest = new YfyAdminDepartmentRequest(internalClient);
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

}
