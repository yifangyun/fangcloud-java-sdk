package com.fangcloud.sdk.auth;

import com.fangcloud.sdk.SdkTestUtil;
import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class YfyEnterpriseAuthTest {
    private static final YfyRequestConfig CONFIG = new YfyRequestConfig();

    @Before
    public void before() throws YfyException {
        String clientId = SdkTestUtil.ENTERPRISE_CLIENT_ID;
        String clientSecret = SdkTestUtil.ENTERPRISE_CLIENT_SECRET;
        YfyAppInfo.initAppInfo(clientId, clientSecret);
    }

    @Test
    public void testLoadPrivateKey() throws Exception {
        PrivateKey key = YfyEnterpriseAuth.loadPrivateKey(YfyEnterpriseAuthTest.class.getResourceAsStream(SdkTestUtil.PRIVATE_KEY_NAME));
        assertEquals(key.getAlgorithm(), "RSA");
    }

    @Test
    public void testGetEnterpriseToken() throws Exception {
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(CONFIG, SdkTestUtil.ENTERPRISE_KID,
                YfyEnterpriseAuth.loadPrivateKey(YfyEnterpriseAuthTest.class.getResourceAsStream(SdkTestUtil.PRIVATE_KEY_NAME)));
        YfyAuthFinish authFinish = enterpriseAuth.getEnterpriseToken(12401L);
        assertNotNull(authFinish);
        assertNotNull(authFinish.getAccessToken());
        assertNotNull(authFinish.getRefreshToken());
    }

    @Test
    public void testGetUserToken() throws Exception {
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(CONFIG, SdkTestUtil.ENTERPRISE_KID,
                YfyEnterpriseAuth.loadPrivateKey(YfyEnterpriseAuthTest.class.getResourceAsStream(SdkTestUtil.PRIVATE_KEY_NAME)));
        YfyAuthFinish authFinish = enterpriseAuth.getUserToken(881525L);
        assertNotNull(authFinish);
        assertNotNull(authFinish.getAccessToken());
        assertNotNull(authFinish.getRefreshToken());
    }
}
