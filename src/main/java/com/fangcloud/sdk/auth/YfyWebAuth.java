package com.fangcloud.sdk.auth;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.YfyRequestUtil;
import com.fangcloud.sdk.YfySessionStore;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.util.StringUtil;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static com.fangcloud.sdk.util.StringUtil.jq;

/**
 * Does the OAuth 2 "authorization code" flow.  (This SDK does not support the "token" flow.)
 */
public class YfyWebAuth {
    private static final SecureRandom RAND = new SecureRandom();
    private static final int CSRF_BYTES_SIZE = 16;
    private static final int CSRF_STRING_SIZE = StringUtil.urlSafeBase64Encode(new byte[CSRF_BYTES_SIZE]).length();

    private final YfyRequestConfig requestConfig;

    /**
     * Creates a new instance that will perform the OAuth2 authorization flow using the given OAuth
     * request configuration.
     *
     * @param requestConfig HTTP request configuration, never {@code null}.
     */
    public YfyWebAuth(YfyRequestConfig requestConfig) {
        if (requestConfig == null) throw new NullPointerException("requestConfig");

        this.requestConfig = requestConfig;
    }

    /**
     * Starts authorization and returns a "authorization URL" on the Fangcloud website that gives the
     * lets the user grant your app access to their Fangcloud account.
     *
     * <p> If a redirect URI was specified ({@link Request.Builder#withRedirectUri}), then users
     * will be redirected to the redirect URI after completing the authorization flow. Call {@link
     * #finishFromRedirect} with the query parameters received from the redirect.
     *
     * <p> If no redirect URI was specified ({@link Request.Builder#withNoRedirect}), then users who
     * grant access will be shown an "authorization code". The user must copy/paste the
     * authorization code back into your app, at which point you can call {@link
     * #finishFromCode(String)} to get an access token.
     *
     * @param request OAuth 2.0 web-based authorization flow request configuration
     *
     * @return Authorization URL of website user can use to authorize your app.
     */
    public String authorize(Request request) {
        return authorizeImpl(request);
    }

    private String authorizeImpl(Request request) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("client_id", YfyAppInfo.getKey());
        params.put("response_type", "code");

        if (request.redirectUri != null) {
            params.put("redirect_uri", request.redirectUri);
            // if (request.state != null) {
            //     params.put("state", request.state);
            // }
            params.put("state", appendCsrfToken(request));
        }

        return YfyRequestUtil.buildUrlWithParams(
                YfyAppInfo.getHost().getAuth(),
                "oauth/authorize",
                params
        );
    }

    private static String appendCsrfToken(Request request) {
        // add a CSRF nonce for security
        byte[] csrf = new byte[CSRF_BYTES_SIZE];
        RAND.nextBytes(csrf);
        String prefix = StringUtil.urlSafeBase64Encode(csrf);

        if (prefix.length() != CSRF_STRING_SIZE) {
            throw new AssertionError("unexpected CSRF token length: " + prefix.length());
        }

        if (request.sessionStore != null) {
            request.sessionStore.set(prefix);
        }

        if (request.state == null) {
            return prefix;
        } else {
            String combined = prefix + request.state;
            if (combined.length() > Request.MAX_STATE_SIZE) {
                throw new AssertionError("unexpected combined state length: " + combined.length());
            }
            return combined;
        }
    }

    /**
     * Call this after the user has visited the authorizaton URL and Fangcloud has redirected them
     * back to you at the redirect URI.
     *
     * @param redirectUri The original redirect URI used by {@link #authorize}, never {@code null}.
     * @param sessionStore Session store used by {@link #authorize} to store CSRF tokens, never
     * {@code null}.
     * @param params The query parameters on the GET request to your redirect URI, never {@code
     * null}.
     *
     * @throws BadRequestException If the redirect request is missing required query parameters,
     * contains duplicate parameters, or includes mutually exclusive parameters (e.g. {@code
     * "error"} and {@code "code"}).
     * @throws BadStateException If the CSRF token retrieved from {@code sessionStore} is {@code
     * null} or malformed.
     * @throws CsrfException If the CSRF token passed in {@code params} does not match the CSRF
     * token from {@code sessionStore}. This implies the redirect request may be forged.
     * @throws YfyException If an error occurs communicating with Fangcloud.
     */
    public YfyAuthFinish finishFromRedirect(String redirectUri,
                                            YfySessionStore sessionStore,
                                            Map<String, String[]> params)
            throws YfyException, BadRequestException, BadStateException, CsrfException {
        if (redirectUri == null) throw new NullPointerException("redirectUri");
        if (sessionStore == null) throw new NullPointerException("sessionStore");
        if (params == null) throw new NullPointerException("params");

        String state = getParam(params, "state");
        if (state == null) {
            throw new BadRequestException("Missing required parameter: \"state\".");
        }

        String code = getParam(params, "code");

        if (code == null) {
            throw new BadRequestException("Missing \"code\".");
        }

        verifyAndStripCsrfToken(state, sessionStore);

        return finish(code, redirectUri);
    }

    /**
     * Call this after the user has visited the authorizaton URL and copy/pasted the authorization
     * code that Fangcloud gave them.
     *
     * @param code The authorization code shown to the user when they clicked "Allow" on the
     *    authorization, page on the Fangcloud website, never {@code null}.
     *
     * @throws YfyException if an error occurs communicating with Fangcloud.
     */
    public YfyAuthFinish finishFromCode(String code) throws YfyException {
        return finish(code, null);
    }

    private String getParam(Map<String,String[]> params, String name) throws BadRequestException {
        String[] v = params.get(name);

        if (v == null) {
            return null;
        } else if (v.length == 0) {
            throw new IllegalArgumentException("Parameter \"" + name + "\" missing value.");
        } else if (v.length == 1) {
            return v[0];
        } else {
            throw new BadRequestException("multiple occurrences of \"" + name + "\" parameter");
        }
    }

    private static String verifyAndStripCsrfToken(String state, YfySessionStore sessionStore)
            throws CsrfException, BadStateException {
        String expected = sessionStore.get();
        if (expected == null) {
            throw new BadStateException("No CSRF Token loaded from session store.");
        }
        if (expected.length() < CSRF_STRING_SIZE) {
            throw new BadStateException("Token retrieved from session store is too small: " + expected);
        }

        if (state.length() < CSRF_STRING_SIZE) {
            throw new CsrfException("Token too small: " + state);
        }

        String actual = state.substring(0, CSRF_STRING_SIZE);
        if (!StringUtil.secureStringEquals(expected, actual)) {
            throw new CsrfException("expecting " + jq(expected) + ", got " + jq(actual));
        }

        String stripped = state.substring(CSRF_STRING_SIZE, state.length());

        sessionStore.clear();

        return stripped.isEmpty() ? null : stripped;
    }

    private YfyAuthFinish finish(String code, String redirectUri) throws YfyException {
        if (code == null) throw new NullPointerException("code");

        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);

        if (redirectUri != null) {
            params.put("redirect_uri", redirectUri);
        }

        return YfyRequestUtil.doPostInAuth(
                requestConfig,
                YfyAppInfo.getHost().getAuth(),
                "oauth/token",
                params,
                YfyAuthFinish.class);
    }

    /**
     * Thrown when the parameters passed to your redirect URI are not well-formed.
     *
     * <p>
     * IMPORTANT: The exception's message must not be shown the the user, but may be logged.
     * </p>
     *
     * <p>
     * The recommended action is to show an HTTP 400 error page.
     * </p>
     */
    public static final class BadRequestException extends Exception {
        private static final long serialVersionUID = 0L;
        public BadRequestException(String message) { super(message); }
    }

    /**
     * Thrown if all the parameters to your redirect URI are well-formed, but there's no CSRF token
     * in the session.  This probably means that the user's session expired and they must restart
     * the OAuth 2 process.
     *
     * <p>
     * IMPORTANT: The exception's message must not be shown the the user, but may be logged.
     * </p>
     *
     * <p>
     * The recommended action is to redirect the user's browser to try the approval process again.
     * </p>
     */
    public static final class BadStateException extends Exception {
        private static final long serialVersionUID = 0L;
        public BadStateException(String message) { super(message); }
    }

    /**
     * Thrown if the given 'state' parameter doesn't contain the expected CSRF token.  This request
     * should be blocked to prevent CSRF attacks.
     *
     * <p>
     * IMPORTANT: The exception's message must not be shown the the user, but may be logged.
     * </p>
     *
     * <p>
     * The recommended action is to show an HTTP 403 error page.
     * </p>
     */
    public static final class CsrfException extends Exception {
        private static final long serialVersionUID = 0L;
        public CsrfException(String message) { super(message); }
    }

    /**
     * Returns a new request builder with default values (e.g. no redirect).
     *
     * @return new request builder with default values
     */
    public static Request.Builder newRequestBuilder() {
        return Request.newBuilder();
    }

    /**
     * OAuth web-based authorization flow request.
     *
     * Used by {@link #authorize} for initiating OAuth web-based authorization flows.
     */
    public static final class Request {
        private static final Charset UTF8 = Charset.forName("UTF-8");
        private static final int MAX_STATE_SIZE = 500;

        private final String redirectUri;
        private final String state;
        private final YfySessionStore sessionStore;

        private Request(String redirectUri,
                        String state,
                        YfySessionStore sessionStore) {
            this.redirectUri = redirectUri;
            this.state = state;
            this.sessionStore = sessionStore;
        }

        /**
         * Returns a new request builder with default values (e.g. no redirect).
         *
         * @return new request builder with default values
         */
        public static Builder newBuilder() {
            return new Builder();
        }

        /**
         * Builder for OAuth2 requests. Use this builder to configure the OAuth authorization flow.
         */
        public static final class Builder {
            private String redirectUri;
            private String state;
            private YfySessionStore sessionStore;

            /**
             * Do not redirect the user after authorization has completed (default).
             *
             * <p> After a user authorizes the app using the authorization URL, a code will be
             * displayed that they must copy and paste into your app. If you want users to be
             * redirected after authorization back to your app, use {@link #withRedirectUri}
             * instead. Websites should always provide a redirect URI.
             *
             * @return this builder
             */
            public Builder withNoRedirect() {
                this.redirectUri = null;
                this.sessionStore = null;
                return this;
            }

            /**
             * Where to redirect the user after authorization has completed.
             *
             * <p> This must be the exact URI registered in the <a href="https://www.fangcloud.com/developers/apps">App Console</a>;
             * even {@code "localhost"} must be listed if it is used for testing. All redirect URIs must be
             * HTTPS except for localhost URIs. If the redirect URI is omitted, the code will be
             * presented directly to the user and they will be invited to enter the information in
             * your app.
             *
             * <p> The given session store will be used for storing the Cross-Site Request Forgery
             * (CSRF) nonce generated during the authorization flow. To prevent CSRF attacks, {@link
             * YfyWebAuth} appends a nonce to each authorization request. When the authorization
             * flow is complete, the returned nonce is compared with the one in the store to ensure
             * the response is valid. A session store <b>must</b> be specified if a redirect URI is
             * set.
             *
             * @param redirectUri URI to redirect authorization response, never {@code null}.
             * @param sessionStore Session store to use for storing CSRF nonces across requests, never {@code null}.
             *
             * @return this builder
             *
             * @throws NullPointerException if either redirectUri or sessionStore is {@code null}
             */
            public Builder withRedirectUri(String redirectUri, YfySessionStore sessionStore) {
                if (redirectUri == null) throw new NullPointerException("redirectUri");
                if (sessionStore == null) throw new NullPointerException("sessionStore");

                this.redirectUri = redirectUri;
                this.sessionStore = sessionStore;

                return this;
            }

            /**
             * Up to 476 bytes of arbitrary data that will be passed back to your redirect URI.
             *
             * <p> Note that {@link YfyWebAuth} will always automatically append a nonce to the
             * state to protect against cross-site request forgery. This is true even if no state is
             * provided.
             *
             * <p> State should only be provided if a redirect URI is provided as well, otherwise
             * {@link #build} will throw an {@link IllegalStateException}.
             *
             * @param state additional state to pass back to the redirect URI, or {@code null} to
             * pass back no additional state.
             *
             * @return this builder
             *
             * @throws IllegalArgumentException if state is greater than 476 bytes
             */
            public Builder withState(String state) {
                if (state != null && (state.getBytes(UTF8).length + CSRF_STRING_SIZE) > MAX_STATE_SIZE) {
                    throw new IllegalArgumentException("UTF-8 encoded state cannot be greater than " + (MAX_STATE_SIZE - CSRF_STRING_SIZE) + " bytes.");
                }
                this.state = state;
                return this;
            }

            /**
             * Returns a new OAuth {@link Request} that can be used in
             * {@link YfyWebAuth#YfyWebAuth(YfyRequestConfig)} to authorize a user.
             *
             * @return new OAuth {@link Request} configuration.
             *
             * @throws IllegalStateException if {@link #withState} was called with a non-{@code
             * null} value, but no redirect URI was specified.
             */
            public Request build() {
                if (redirectUri == null && state != null) {
                    throw new IllegalStateException("Cannot specify a state without a redirect URI.");
                }

                return new Request(redirectUri,
                        state,
                        sessionStore);
            }
        }
    }

}
