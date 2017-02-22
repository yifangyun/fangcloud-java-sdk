package com.fangcloud.sdk.auth;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.YfySessionStore;
import com.fangcloud.sdk.http.HttpRequestor;
import com.fangcloud.sdk.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YfyWebAuthTest {
    private static final YfyRequestConfig CONFIG = new YfyRequestConfig();

    @Before
    public void before() {
        YfyAppInfo.initAppInfo("test-key", "test-secret");
    }

    @Test
    public void testAuthUrl() {
        String redirectUri = "https://localhost/auth/finish";
        YfySessionStore sessionStore = new SimpleSessionStore();
        String state = "test-state";

        YfyWebAuth auth = new YfyWebAuth(CONFIG);
        String authUrl = auth.authorize(
                YfyWebAuth.newRequestBuilder()
                        .withRedirectUri(redirectUri, sessionStore)
                        .withState(state)
                        .build()
        );
        assertAuthorizationUrls(
                "https://auth.fangcloud.com/oauth/authorize?response_type=code&redirect_uri=https://localhost/auth/finish&state=8mEcdvKM5QRH0LHg0cJmAA==test-state&client_id=test-key",
                authUrl);

        authUrl = auth.authorize(
                YfyWebAuth.newRequestBuilder()
                        .withRedirectUri(redirectUri, sessionStore)
                        .build()
        );

        assertAuthorizationUrls(
                "https://auth.fangcloud.com/oauth/authorize?response_type=code&redirect_uri=https://localhost/auth/finish&state=9WHp6HRzD9r09YgAHYVz_Q==&client_id=test-key",
                authUrl);

        authUrl = auth.authorize(
                YfyWebAuth.newRequestBuilder()
                        .withNoRedirect()
                        .build()
        );

        assertAuthorizationUrls(
                "https://auth.fangcloud.com/oauth/authorize?response_type=code&client_id=test-key",
                authUrl);

    }

    @Test(expected=IllegalArgumentException.class)
    public void testStateTooLarge() {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < 476; ++i) {
            state.append(".");
        }
        try {
            YfyWebAuth.newRequestBuilder()
                    .withRedirectUri("http://localhost/bar", new SimpleSessionStore())
                    .withState(state.toString())
                    .build();
        } catch (IllegalArgumentException ex) {
            fail("Unable to create OAuth request with max state bytes.");
        }

        state.append("."); // one too many, should throw exception
        YfyWebAuth.newRequestBuilder()
                .withRedirectUri("http://localhost/bar", new SimpleSessionStore())
                .withState(state.toString())
                .build();
    }

    private static void assertAuthorizationUrls(String actual, String expected) {
        try {
            URL a = new URL(actual);
            URL b = new URL(expected);

            assertEquals(a.getProtocol(), b.getProtocol());
            assertEquals(a.getAuthority(), b.getAuthority());
            assertEquals(a.getPath(), b.getPath());
            assertEquals(a.getRef(), b.getRef());

            Map<String, List<String>> pa = toParamsMap(new URL(actual));
            Map<String, List<String>> pb = toParamsMap(new URL(expected));

            assertEquals(pa.keySet(), pb.keySet());
            for (String key : pa.keySet()) {
                if ("state".equals(key)) {
                    continue;
                }
                assertEquals(pa.get(key), pb.get(key));
            }
        } catch (Exception ex) {
            fail("Couldn't compare authorization URLs");
        }
    }

    @Test(expected=YfyWebAuth.CsrfException.class)
    public void testCsrfVerifyException() throws Exception {
        YfySessionStore sessionStore = new SimpleSessionStore();
        sessionStore.set(StringUtil.urlSafeBase64Encode(new byte[16]));

        new YfyWebAuth(CONFIG).finishFromRedirect(
                "http://localhost/redirect",
                sessionStore,
                params("code", "test-code",
                        "state", "_no_csrf_available_or_bad_token_|test-state")
        );
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFinishWithState() throws Exception {
        String redirectUri = "http://localhost/finish/with/state/test";
        YfySessionStore sessionStore = new SimpleSessionStore();
        String state = "test-state";

        YfyWebAuth.Request request = YfyWebAuth.newRequestBuilder()
                .withRedirectUri(redirectUri, sessionStore)
                .withState(state)
                .build();

        // simulate a web server that will not keep the YfyWebAuth
        // instance across requests
        String authorizationUrl = new YfyWebAuth(CONFIG).authorize(request);

        assertNotNull(sessionStore.get());

        YfyAuthFinish expected = new YfyAuthFinish("test-access-token", "test-refresh-token", 3600, "Bearer", "all");
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        ByteArrayInputStream responseStream = new ByteArrayInputStream(
                (
                        "{" +
                                "\"token_type\":\"Bearer\"" +
                                ",\"access_token\":\"" + expected.getAccessToken() + "\"" +
                                ",\"refresh_token\":\"" + expected.getRefreshToken() + "\"" +
                                ",\"expires_in\":" + expected.getExpiresIn() +
                                ",\"scope\":\"" + expected.getScope() + "\"" +
                                "}"
                ).getBytes("UTF-8")
        );
        HttpRequestor.Response finishResponse = new HttpRequestor.Response(
                200, responseStream, new HashMap<String, List<String>>());

        HttpRequestor mockRequestor = mock(HttpRequestor.class);
        HttpRequestor.Uploader mockUploader = mock(HttpRequestor.Uploader.class);
        when(mockUploader.getBody())
                .thenReturn(body);
        when(mockUploader.finish())
                .thenReturn(finishResponse);
        when(mockRequestor.startPost(anyString(), anyString(), anyListOf(HttpRequestor.Header.class)))
                .thenReturn(mockUploader);

        YfyRequestConfig mockConfig = new YfyRequestConfig(mockRequestor);

        YfyAuthFinish actual = new YfyWebAuth(mockConfig).finishFromRedirect(
                redirectUri,
                sessionStore,
                params("code", "test-code",
                        "state", extractQueryParam(authorizationUrl, "state"))
        );

        // verify the state param isn't send to the 'oauth2/token' endpoint
        String finishParams = new String(body.toByteArray(), "UTF-8");
        assertNull(toParamsMap(finishParams).get("state"));

        assertNotNull(actual);
        assertEquals(actual.getAccessToken(), expected.getAccessToken());
        assertEquals(actual.getRefreshToken(), expected.getRefreshToken());
        assertEquals(actual.getExpiresIn(), expected.getExpiresIn());
        assertEquals(actual.getScope(), expected.getScope());
        assertEquals(actual.getTokenType(), expected.getTokenType());
    }

    private static Map<String, String[]> params(String ... pairs) {
        if ((pairs.length % 2) != 0) {
            fail("pairs must be a multiple of 2.");
        }

        Map<String, String[]> query = new HashMap<String, String[]>();
        for (int i = 0; i < pairs.length; i += 2) {
            query.put(pairs[i], new String [] { pairs[i + 1] });
        }
        return query;
    }

    private static String extractQueryParam(URL url, String param) {
        assertNotNull(url.getQuery());
        return extractQueryParam(url.getQuery(), param);
    }

    private static String extractQueryParam(String query, String param) {
        Map<String, List<String>> params = toParamsMap(query);

        if (!params.containsKey(param)) {
            fail("Param \"" + param + "\" not found in: " + query);
            return null;
        }

        List<String> values = params.get(param);
        if (values.size() > 1) {
            fail("Param \"" + param + "\" appears more than once in: " + query);
            return null;
        }

        return values.get(0);
    }

    private static Map<String, List<String>> toParamsMap(URL url) {
        return toParamsMap(url.getQuery());
    }

    private static Map<String, List<String>> toParamsMap(String query) {
        try {
            String [] pairs = query.split("&");
            Map<String, List<String>> params = new HashMap<String, List<String>>(pairs.length);

            for (String pair : pairs) {
                String [] keyValue = pair.split("=", 2);
                String key = keyValue[0];
                String value = keyValue.length == 2 ? keyValue[1] : "";

                List<String> others = params.get(key);
                if (others == null) {
                    others = new ArrayList<String>();
                    params.put(key, others);
                }

                others.add(URLDecoder.decode(value, "UTF-8"));
            }

            return params;
        } catch (Exception ex) {
            fail("Couldn't build query parameter map from: " + query);
            return null;
        }
    }

    private static final class SimpleSessionStore implements YfySessionStore {
        private String token;

        public SimpleSessionStore() {
            this.token = null;
        }

        @Override
        public String get() {
            return token;
        }

        @Override
        public void set(String value) {
            this.token = value;
        }

        @Override
        public void clear() {
            this.token = null;
        }
    }

}
