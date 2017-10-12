package com.fangcloud.sdk.auth;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.YfyFileRequestTest;
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
        String clientId= "e885b1d0-39e4-49eb-be06-16078cf3f613";
        String clientSecret= "b366fa56-c50e-4a68-bc12-1044d974d7b8";
        YfyAppInfo.initAppInfo(clientId, clientSecret);
    }

    @Test
    public void testLoadPrivateKey() throws Exception {
        PrivateKey key = YfyEnterpriseAuth.loadPrivateKey(YfyEnterpriseAuthTest.class.getResourceAsStream("/privatekey-pkcs8.pem"));
        assertEquals(key.getAlgorithm(), "RSA");
    }

    @Test
    public void testGetEnterpriseToken() throws Exception {
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(CONFIG, "U7TejSsByn",
                YfyEnterpriseAuth.loadPrivateKey(YfyEnterpriseAuthTest.class.getResourceAsStream("/privatekey-pkcs8.pem")));
        YfyAuthFinish authFinish = enterpriseAuth.getEnterpriseToken(12401);
        assertNotNull(authFinish);
        assertNotNull(authFinish.getAccessToken());
        assertNotNull(authFinish.getRefreshToken());
    }

    @Test
    public void testGetUserToken() throws Exception {
        YfyEnterpriseAuth enterpriseAuth = new YfyEnterpriseAuth(CONFIG, "U7TejSsByn",
                YfyEnterpriseAuth.loadPrivateKey(YfyFileRequestTest.class.getResourceAsStream("/privatekey-pkcs8.pem")));
        YfyAuthFinish authFinish = enterpriseAuth.getUserToken(881525L);
        assertNotNull(authFinish);
        assertNotNull(authFinish.getAccessToken());
        assertNotNull(authFinish.getRefreshToken());
    }
}
