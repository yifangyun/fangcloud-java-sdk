package com.fangcloud.sdk.api;

import com.fangcloud.sdk.SdkTestUtil;
import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyEnterpriseClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.admin.platform.YfyAdminPlatformRequest;
import com.fangcloud.sdk.api.admin.user.YfyDetailedUser;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.auth.YfyEnterpriseAuth;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertDetailedUserNotNull;

public class YfyAdminPlatformTest {
    private static YfyAdminPlatformRequest adminPlatformRequest;

    @BeforeClass
    public static void before() throws Exception {
        YfyAppInfo.initAppInfo(SdkTestUtil.ENTERPRISE_CLIENT_ID, SdkTestUtil.ENTERPRISE_CLIENT_SECRET);
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(new YfyRequestConfig(), SdkTestUtil.ENTERPRISE_KID,
                YfyEnterpriseAuth.loadPrivateKey(YfyAdminDepartmentTest.class.getResourceAsStream(SdkTestUtil.PRIVATE_KEY_NAME)));
        YfyAuthFinish authFinish = enterpriseAuth.getEnterpriseToken(SdkTestUtil.ENTERPRISE_ID);
        YfyEnterpriseClient enterpriseClient = new YfyEnterpriseClient(new YfyRequestConfig(), authFinish.getAccessToken());
        adminPlatformRequest = enterpriseClient.adminPlatforms();
    }

    @Test
    public void testGetMappingUser() throws YfyException {
        YfyDetailedUser detailedUser = adminPlatformRequest.getMappingUser(89L, "hjstest7");
        assertDetailedUserNotNull(detailedUser);
    }

}
