package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyEnterpriseClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.admin.department.AdminDepartmentUserResult;
import com.fangcloud.sdk.api.admin.department.YfyAdminDepartmentRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class YfyAdminDepartmentTest {
    private YfyAdminDepartmentRequest adminDepartmentRequest;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("test-client", "123456");
        YfyEnterpriseClient enterpriseClient = new YfyEnterpriseClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        adminDepartmentRequest = enterpriseClient.adminDepartments();
    }

    @Test
    public void testGetDepartmentUsers() throws YfyException {
        AdminDepartmentUserResult result = adminDepartmentRequest.getDepartmentUsers(0, "test", 0);
        assertNotNull(result);
    }

}
