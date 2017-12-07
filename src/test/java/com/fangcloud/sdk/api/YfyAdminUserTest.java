package com.fangcloud.sdk.api;

import com.fangcloud.sdk.SdkTestUtil;
import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyEnterpriseClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.admin.user.YfyAdminUserRequest;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.auth.YfyEnterpriseAuth;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class YfyAdminUserTest {
    private static YfyAdminUserRequest adminUserRequest;

    @BeforeClass
    public static void before() throws Exception {
        YfyAppInfo.initAppInfo(SdkTestUtil.ENTERPRISE_CLIENT_ID, SdkTestUtil.ENTERPRISE_CLIENT_SECRET);
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(new YfyRequestConfig(), SdkTestUtil.ENTERPRISE_KID,
                YfyEnterpriseAuth.loadPrivateKey(YfyAdminDepartmentTest.class.getResourceAsStream(SdkTestUtil.PRIVATE_KEY_NAME)));
        YfyAuthFinish authFinish = enterpriseAuth.getEnterpriseToken(SdkTestUtil.ENTERPRISE_ID);
        YfyEnterpriseClient enterpriseClient = new YfyEnterpriseClient(new YfyRequestConfig(), authFinish.getAccessToken());
        adminUserRequest = enterpriseClient.adminUsers();
    }

    @Test
    public void testDeleteUser() throws YfyException {
        SuccessResult result = adminUserRequest.deleteUser(1012200L, null);
        assertTrue(result.getSuccess());
    }

}
