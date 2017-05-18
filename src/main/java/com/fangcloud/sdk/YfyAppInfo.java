package com.fangcloud.sdk;

import static com.fangcloud.sdk.util.StringUtil.jq;

/**
 * Identifying information about your application.
 */
public class YfyAppInfo {
    private static final String TEST_FLAG="FangcloudTest";
    private YfyAppInfo() {}

    private static String key;
    private static String secret;
    private static YfyHost host;

    /**
     * Must be invoked before sending any request
     *
     * @param key Fangcloud app key (see {@link #getKey})
     * @param secret Fangcloud app secret (see {@link #getSecret})
     */
    public static void initAppInfo(String key, String secret) {
        YfyHost host = YfyHost.DEFAULT;
        if (System.getenv().get(TEST_FLAG) != null) {
            host = new YfyHost("platform.fangcloud.net", "oauth-server.fangcloud.net");
        }
        initAppInfo(key, secret, host);
    }

    private static void initAppInfo(String key, String secret, YfyHost host) {
        checkKeyArg(key);
        checkSecretArg(secret);

        YfyAppInfo.key = key;
        YfyAppInfo.secret = secret;
        YfyAppInfo.host = host;
    }

    /**
     * Returns the Fangcloud <em>app key</em> (OAuth calls this the <em>consumer key</em>).  You can
     * create an app key and secret on the <a href="http://fangcloud.com/developers/apps">Fangcloud
     * developer website</a>.
     *
     * @return Fangcloud app key
     */
    public static String getKey() {
        checkKeyArg(key);
        return key;
    }

    /**
     * Returns the Fangcloud <em>app secret</em> (OAuth calls this the <em>consumer secret</em>).  You
     * can create an app key and secret on the <a href="http://fangcloud.com/developers/apps">Fangcloud
     * developer website</a>.
     *
     * <p> Make sure that this is kept a secret.  Someone with your app secret can impesonate your
     * application.  People sometimes ask for help on the Fangcloud API forums and copy/paste their
     * code, which sometimes includes their app secret.  Do not do that.  </p>
     *
     * @return Fangcloud app secret
     */
    public static String getSecret() {
        checkSecretArg(secret);
        return secret;
    }

    /**
     * Returns the Fangcloud host configuration.
     *
     * <p> This is almost always {@link YfyHost#DEFAULT}.  Typically this value will only be
     * different for testing purposes.
     *
     * @return Fangcloud host configuration
     */
    public static YfyHost getHost() {
        return host;
    }

    public static String getTokenPartError(String s) {
        if (s == null) return "can't be null";
        if (s.length() == 0) return "can't be empty";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 0x21 || c > 0x7e) {
                // Only allow normal visible ASCII characters.
                return "invalid character at index " + i + ": " + jq("" + c);
            }
        }
        return null;
    }

    public static void checkKeyArg(String key) {
        String error = getTokenPartError(key);
        if (error == null) return;
        throw new IllegalArgumentException("app info not init 'key': " + error);
    }

    public static void checkSecretArg(String secret) {
        String error = getTokenPartError(secret);
        if (error == null) return;
        throw new IllegalArgumentException("app info not init 'secret': " + error);
    }
}
