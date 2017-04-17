package com.fangcloud.sdk;

import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.exception.InvalidTokenException;
import com.fangcloud.sdk.exception.NeedAuthorizationException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.http.HttpRequestor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class YfyClientTest {
    private String userName = "wien";

    @Before
    public void before() {
        YfyAppInfo.initAppInfo("test-key", "test-secret");
    }

    @Test(expected = InvalidTokenException.class)
    @SuppressWarnings("deprecation")
    public void testAutoFreshDisabled() throws YfyException, IOException {
        HttpRequestor mockRequestor = mock(HttpRequestor.class);
        YfyRequestConfig config = new YfyRequestConfig(mockRequestor);

        YfyClient<String> client = new YfyClient<String>(userName, config, "fakeAccessToken", "fakeFreshToken");

        ByteArrayInputStream responseStream = new ByteArrayInputStream(
                (
                        "{" +
                                "\"errors\":[" +
                                "{\"code\": \"invalid_token\"" +
                                ",\"msg\": \"Invalid token: fakeAccessToken\"" +
                                "}]" +
                                ",\"request_id\": \"4021b508-b435-4d45-92f2-271c203d260e\"" +
                                "}"
                ).getBytes("UTF-8")
        );
        HttpRequestor.Response finishResponse = new HttpRequestor.Response(
                401, responseStream, new HashMap<String, List<String>>());
        when(mockRequestor.doGet(anyString(), anyListOf(HttpRequestor.Header.class)))
                .thenReturn(finishResponse);

        try {
            client.files().getFile(12345L);
        } finally {
            // should only have been called once since we disabled auto refresh
            verify(mockRequestor, times(1)).doGet(anyString(), anyListOf(HttpRequestor.Header.class));
        }
    }

    @Test(expected = NeedAuthorizationException.class)
    @SuppressWarnings("deprecation")
    public void testAutoRefreshEnabled() throws YfyException, IOException {
        HttpRequestor mockRequestor = mock(HttpRequestor.class);
        YfyRequestConfig config = new YfyRequestConfig(mockRequestor);

        final YfyAuthFinish expectedAuthFinish = new YfyAuthFinish("test-access-token", "test-refresh-token", 3600, "Bearer", "all");
        YfyFile expectedFile = new YfyFile();
        expectedFile.setId(12345L);
        expectedFile.setName("哈哈");
        expectedFile.setSize(54321L);
        expectedFile.setCreatedAt(1234567890L);

        YfyRefreshListener<String> refreshListener = new YfyRefreshListener<String>() {
            @Override
            public void tokenRefresh(String key, String accessToken, String refreshToken, long expireIn) {
                assertEquals(key, userName);
                assertEquals(accessToken, expectedAuthFinish.getAccessToken());
                assertEquals(refreshToken, expectedAuthFinish.getRefreshToken());
                assertEquals(expireIn, expectedAuthFinish.getExpiresIn());
            }
        };

        YfyClient<String> client = new YfyClient<String>(userName, config, "fakeAccessToken", "fakeFreshToken", refreshListener);

        byte[] apiFailByte = (
                "{" +
                        "\"errors\":[" +
                        "{\"code\": \"invalid_token\"" +
                        ",\"msg\": \"Invalid token: fakeAccessToken\"" +
                        "}]" +
                        ",\"request_id\": \"4021b508-b435-4d45-92f2-271c203d260e\"" +
                        "}"
        ).getBytes("UTF-8");
        byte[] apiSuccessByte = (
                "{" +
                        "\"id\": " + expectedFile.getId() +
                        ",\"name\": \"" + expectedFile.getName() + "\"" +
                        ",\"size\": " + expectedFile.getSize() +
                        ",\"created_at\": " + expectedFile.getCreatedAt() +
                        "}"
        ).getBytes("UTF-8");

        // first fail, then success
        when(mockRequestor.doGet(anyString(), anyListOf(HttpRequestor.Header.class)))
                .thenReturn(createResponse(apiFailByte, 401))
                .thenReturn(createResponse(apiSuccessByte, 200));

        ByteArrayOutputStream body = new ByteArrayOutputStream();
        byte[] authResponseByte = (
                "{" +
                        "\"token_type\":\"Bearer\"" +
                        ",\"access_token\":\"" + expectedAuthFinish.getAccessToken() + "\"" +
                        ",\"refresh_token\":\"" + expectedAuthFinish.getRefreshToken() + "\"" +
                        ",\"expires_in\":" + expectedAuthFinish.getExpiresIn() +
                        ",\"scope\":\"" + expectedAuthFinish.getScope() + "\"" +
                        "}"
        ).getBytes("UTF-8");

        HttpRequestor.Uploader mockUploader = mock(HttpRequestor.Uploader.class);
        when(mockUploader.getBody())
                .thenReturn(body);
        when(mockUploader.finish())
                .thenReturn(createResponse(authResponseByte, 200));
        when(mockRequestor.startPost(anyString(), anyListOf(HttpRequestor.Header.class)))
                .thenReturn(mockUploader);

        try {
            YfyFile actualFile = client.files().getFile(12345L);
            assertEquals(actualFile.getId(), expectedFile.getId());
            assertEquals(actualFile.getName(), expectedFile.getName());
            assertEquals(actualFile.getSize(), expectedFile.getSize());
            assertEquals(actualFile.getCreatedAt(), expectedFile.getCreatedAt());
        } finally {
            verify(mockRequestor, times(2)).doGet(anyString(), anyListOf(HttpRequestor.Header.class));
        }

        // first fail, then fail
        when(mockRequestor.doGet(anyString(), anyListOf(HttpRequestor.Header.class)))
                .thenReturn(createResponse(apiFailByte, 401))
                .thenReturn(createResponse(apiFailByte, 401));

        try {
            client.files().getFile(12345L);
        } finally {
            verify(mockRequestor, times(4)).doGet(anyString(), anyListOf(HttpRequestor.Header.class));
        }
    }

    private static HttpRequestor.Response createResponse(byte[] body, int statusCode) throws IOException {
        return new HttpRequestor.Response(
                statusCode,
                new ByteArrayInputStream(body),
                Collections.<String,List<String>>emptyMap()
        );
    }
}
