package com.fangcloud.core.sdk;

public final class YfyHost {
    /**
     * The standard Fangcloud hosts: "api.fangcloud.com", "api-content.fangcloud.com",
     * and "www.fangcloud.com"
     */
    public static final YfyHost DEFAULT = new YfyHost(
            "open.fangcloud.com",
            "oauth.fangcloud.com"
    );

    private final String api;
    private final String auth;

    /**
     * @param api main Fangcloud API server host name
     */
    public YfyHost(String api, String auth) {
        this.api = api;
        this.auth = auth;
    }

    /**
     * Returns the host name of the main Fangcloud open api server.
     * The default is {@code "open.fangcloud.com"}.
     *
     * @return host name of main Fangcloud API server
     */
    public String getApi() {
        return api;
    }

    /**
     * Returns the host name of the Fangcloud auth server.  Used during user authorization.
     * The default is {@code "auth.fangcloud.com"}.
     *
     * @return host name of Fangcloud API auth server used during user authorization
     */
    public String getAuth() {
        return auth;
    }

    @Override
    public int hashCode() {
        return api.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if(obj instanceof YfyHost) {
            YfyHost other = (YfyHost) obj;
            return other.api.equals(this.api);
        } else {
            return false;
        }
    }
}
