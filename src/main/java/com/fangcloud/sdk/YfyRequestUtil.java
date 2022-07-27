package com.fangcloud.sdk;

import com.fangcloud.sdk.exception.BadResponseException;
import com.fangcloud.sdk.exception.InvalidTokenException;
import com.fangcloud.sdk.exception.JsonReadException;
import com.fangcloud.sdk.exception.NetworkIOException;
import com.fangcloud.sdk.exception.NormalException;
import com.fangcloud.sdk.exception.RateLimitException;
import com.fangcloud.sdk.exception.ServerException;
import com.fangcloud.sdk.exception.OtherErrorException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.http.HttpRequestor;
import com.fangcloud.sdk.util.IOUtil;
import com.fangcloud.sdk.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fangcloud.sdk.util.LangUtil.mkAssert;
import static com.fangcloud.sdk.util.StringUtil.jq;

public final class YfyRequestUtil {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static String buildUrlWithParams(String host,
                                            String path,
                                            Map<String, String> params) {
        String paramString = encodeUrlParams(params);
        if (!"".equals(paramString)) {
            paramString = "?" + paramString;
        }
        return buildUri(host, path) + paramString;
    }

    public static String buildUri(String host, String path) {
        try {
            String protocol;
            if (host.contains("-svc")) {
                protocol = YfySdkConstant.SCHEME_HTTP;
            } else {
                protocol = YfySdkConstant.SCHEME_HTTPS;
            }
            if(YfyAppInfo.getProtocol() != null){
                protocol =  YfyAppInfo.getProtocol();
            }
            return protocol + "://" + host + new URI(null, null, "/" + path, null).toASCIIString();
        }
        catch (URISyntaxException ex) {
            throw mkAssert("URI creation failed, host=" + jq(host) + ", path=" + jq(path), ex);
        }
    }

    private static String encodeUrlParams(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        String sep = "";
        StringBuilder buf = new StringBuilder("");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buf.append(sep).append(entry.getKey()).append("=").append(entry.getValue());
            sep = "&";
        }

        return buf.toString();
    }

    public static String encodeUrlParam(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw mkAssert("UTF-8 should always be supported", ex);
        }
    }

    public static List<HttpRequestor.Header> addBasicAuthHeader(List<HttpRequestor.Header> headers, String username, String password) {
        if (username == null) throw new NullPointerException("username");
        if (password == null) throw new NullPointerException("password");
        if (headers == null) headers = new ArrayList<HttpRequestor.Header>();

        String credentials = username + ":" + password;
        String base64Credentials = StringUtil.base64Encode(StringUtil.stringToUtf8(credentials));
        headers.add(new HttpRequestor.Header("Authorization", "Basic " + base64Credentials));;
        return headers;
    }

    public static List<HttpRequestor.Header> addAuthHeader(List<HttpRequestor.Header> headers, String accessToken) {
        if (accessToken == null) throw new NullPointerException("accessToken");
        if (headers == null) headers = new ArrayList<HttpRequestor.Header>();

        headers.add(new HttpRequestor.Header("Authorization", "Bearer " + accessToken));
        return headers;
    }

    public static List<HttpRequestor.Header> addApiCustomHeader(List<HttpRequestor.Header> headers,
                                                                String sdkUserAgentIdentifier) {
        headers.add(new HttpRequestor.Header("User-Agent",
                YfyAppInfo.getKey() + " " + sdkUserAgentIdentifier + "/" + YfySdkVersion.Version));
        headers.add(new HttpRequestor.Header("X-Runtime-Version", YfySdkVersion.JavaRuntimeVersion));
        return headers;
    }

    public static List<HttpRequestor.Header> addDownloadCustomHeader(List<HttpRequestor.Header> headers) {
        headers.add(new HttpRequestor.Header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"));
        return headers;
    }

    public static <T> T doGetNoAuth(YfyRequestConfig requestConfig,
                                    String host,
                                    String path,
                                    Map<String, String> params,
                                    List<HttpRequestor.Header> headers,
                                    Class<T> tClass)
            throws YfyException {
        String uri = buildUrlWithParams(host, path, params);
        try {
            HttpRequestor.Response response = requestConfig.getHttpRequestor().doGet(uri, headers);
            return finishResponse(response, tClass);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    /**
     * only use in oauth server, user content-type application/x-www-form-urlencoded
     */
    public static <T> T doPostInAuth(YfyRequestConfig requestConfig,
                                     String host,
                                     String path,
                                     Map<String, String> params,
                                     Class<T> tClass)
            throws YfyException {
        List<HttpRequestor.Header> headers = new ArrayList<HttpRequestor.Header>();
        YfyRequestUtil.addBasicAuthHeader(headers, YfyAppInfo.getKey(), YfyAppInfo.getSecret());

        byte[] encodedParams = StringUtil.stringToUtf8(encodeUrlParams(params));

        headers.add(new HttpRequestor.Header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        headers.add(new HttpRequestor.Header("Accept", "application/json"));
        HttpRequestor.Response response = startPostRaw(requestConfig, host, path, encodedParams, headers);
        return finishResponse(response, tClass);
    }

    /**
     * only use in api server, user content-type application/json
     */
    public static <T> T doPostNoAuth(YfyRequestConfig requestConfig,
                                     String host,
                                     String path,
                                     YfyArg arg,
                                     List<HttpRequestor.Header> headers,
                                     Class<T> tClass)
            throws YfyException {
        byte[] encodedParams = StringUtil.stringToUtf8(convertObjToJson(arg));

        headers.add(new HttpRequestor.Header("Content-Type", "application/json; charset=utf-8"));
        HttpRequestor.Response response = startPostRaw(requestConfig, host, path, encodedParams, headers);
        return finishResponse(response, tClass);
    }
    /**
     * Convenience function for making HTTP POST requests.  Like startPostNoAuth but takes byte[] instead of params.
     */
    private static HttpRequestor.Response startPostRaw(YfyRequestConfig requestConfig,
                                                      String host,
                                                      String path,
                                                      byte[] body,
                                                      List<HttpRequestor.Header> headers)
            throws NetworkIOException {
        String uri = buildUri(host, path);

        headers = copyHeaders(headers);
        headers.add(new HttpRequestor.Header("Content-Length", Integer.toString(body.length)));

        try {
            HttpRequestor.Uploader uploader = requestConfig.getHttpRequestor().startPost(uri, headers);
            try {
                uploader.upload(body);
                return uploader.finish();
            } finally {
                uploader.close();
            }
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    private static List<HttpRequestor.Header> copyHeaders(List<HttpRequestor.Header> headers) {
        if (headers == null) {
            return new ArrayList<HttpRequestor.Header>();
        } else {
            return new ArrayList<HttpRequestor.Header>(headers);
        }
    }

    public static <T> T finishResponse(HttpRequestor.Response response, Class<T> tClass) throws YfyException {
        try {
            if (response.getStatusCode() != 200) {
                throw unexpectedStatus(response);
            }
            return convertStreamToObj(response.getBody(), tClass);
        } catch (JsonReadException ex) {
            throw new BadResponseException("Bad JSON in response : " + ex.getMessage(), ex);
        } finally {
            IOUtil.closeInput(response.getBody());
        }
    }

    public static YfyException unexpectedStatus(HttpRequestor.Response response)
            throws NetworkIOException, JsonReadException {
        YfyErrorResponse errorResponse;
        try {
            errorResponse = convertStreamToObj(response.getBody(), YfyErrorResponse.class);
        } catch (JsonReadException ex) {
            // deal with download and upload error response
            if (ex.getJsonStr().startsWith("<!DOCTYPE html>")) {
                return new OtherErrorException("download url is invalid or expired!\ndetailed info:" + ex.getJsonStr());
            }
            YfyUploadErrorResponse uploadErrorResponse = convertStrToObj(ex.getJsonStr(), YfyUploadErrorResponse.class);
            return new OtherErrorException(uploadErrorResponse.getError());
        }

        switch (response.getStatusCode()) {
            case 401:
                return new InvalidTokenException(errorResponse);
            case 429:
                return new RateLimitException(errorResponse, response.getHeaders());
            case 500:
                return new ServerException(errorResponse);
        }
        return new NormalException(errorResponse);
    }

    private static <T> T convertStreamToObj(InputStream inputStream, Class<T> tClass)
            throws NetworkIOException, JsonReadException {
        String jsonStr = null;
        try {
            // if read input stream directly, the stream can only be read once
            jsonStr = IOUtil.toUtf8String(inputStream);
            return convertStrToObj(jsonStr, tClass);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    private static <T> T convertStrToObj(String jsonStr, Class<T> tClass)
            throws NetworkIOException, JsonReadException {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, tClass);
        } catch (JsonProcessingException ex) {
            throw new JsonReadException(jsonStr, ex);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        }
    }

    private static String convertObjToJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
