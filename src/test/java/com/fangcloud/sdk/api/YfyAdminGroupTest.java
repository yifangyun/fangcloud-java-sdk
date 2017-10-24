package com.fangcloud.sdk.api;

import com.fangcloud.sdk.SdkTestUtil;
import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyEnterpriseClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.admin.group.AdminGroupListResult;
import com.fangcloud.sdk.api.admin.group.YfyAdminGroupRequest;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.auth.YfyEnterpriseAuth;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.BeforeClass;
import org.junit.Test;

public class YfyAdminGroupTest {
    private static YfyAdminGroupRequest adminGroupRequest;

    @BeforeClass
    public static void before() throws Exception {
        String clientId= "e885b1d0-39e4-49eb-be06-16078cf3f613";
        String clientSecret= "b366fa56-c50e-4a68-bc12-1044d974d7b8";
        YfyAppInfo.initAppInfo(clientId, clientSecret);
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(new YfyRequestConfig(), "U7TejSsByn",
                YfyEnterpriseAuth.loadPrivateKey(YfyAdminDepartmentTest.class.getResourceAsStream("/privatekey-pkcs8.pem")));
        YfyAuthFinish authFinish = enterpriseAuth.getEnterpriseToken(12401);
        YfyEnterpriseClient enterpriseClient = new YfyEnterpriseClient(new YfyRequestConfig(), authFinish.getAccessToken());
        adminGroupRequest = enterpriseClient.adminGroups();
    }

    @Test
    public void testGetGroupList() throws YfyException {
        AdminGroupListResult result = adminGroupRequest.getGrouplist("test", 0);
        SdkTestUtil.assertPagingResultNotNull(result);
        for (YfyDetailedGroup detailedGroup : result.getGroups()) {
            SdkTestUtil.assertDetailedGroupNotNull(detailedGroup);
        }
    }

}
