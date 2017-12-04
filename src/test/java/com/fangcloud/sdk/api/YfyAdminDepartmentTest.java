package com.fangcloud.sdk.api;

import com.fangcloud.sdk.SdkTestUtil;
import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyEnterpriseClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.admin.department.AdminDepartmentUserResult;
import com.fangcloud.sdk.api.admin.department.YfyAdminDepartmentRequest;
import com.fangcloud.sdk.api.user.YfyUser;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.auth.YfyEnterpriseAuth;
import com.fangcloud.sdk.auth.YfyEnterpriseAuthTest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class YfyAdminDepartmentTest {
    private static YfyAdminDepartmentRequest adminDepartmentRequest;

    @BeforeClass
    public static void before() throws Exception {
        YfyAppInfo.initAppInfo(SdkTestUtil.ENTERPRISE_CLIENT_ID, SdkTestUtil.ENTERPRISE_CLIENT_SECRET);
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(new YfyRequestConfig(), SdkTestUtil.ENTERPRISE_KID,
                YfyEnterpriseAuth.loadPrivateKey(YfyAdminDepartmentTest.class.getResourceAsStream("/privatekey-pkcs8.pem")));
        YfyAuthFinish authFinish = enterpriseAuth.getEnterpriseToken(SdkTestUtil.ENTERPRISE_ID);
        YfyEnterpriseClient enterpriseClient = new YfyEnterpriseClient(new YfyRequestConfig(), authFinish.getAccessToken());
        adminDepartmentRequest = enterpriseClient.adminDepartments();
    }

    @Test
    public void testGetDepartmentUsers() throws YfyException {
        AdminDepartmentUserResult result = adminDepartmentRequest.getDepartmentUsers(0, "test", 0);
        assertNotNull(result);
        for (YfyMiniUser user : result.getUsers()) {
            SdkTestUtil.assertMiniUserNotNull(user);
        }
    }

}
